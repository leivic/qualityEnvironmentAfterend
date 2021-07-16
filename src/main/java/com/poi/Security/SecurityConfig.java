package com.poi.Security;

import com.poi.Security.custom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//该类是配置类 功能和xml文件相当 配置类就可以在里面定义bean 该类装配成一个bean 装进spring里面 要让配置类生效 同样要取出bean来使用才行 看不见哪里在取 说明被封装了 是被依赖（本项目是springsecurity）给封装的
//别纠结配置类写了很多方法等 这个方法也就是这个类的方法 配置类@Configuration注解在类上 就是在创建polo元数据的同时生成bean @bean注解在方法上，就仍需要额外的polo元数据 生成的bean是return的类型
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启基于方法的安全认证机制
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailHandler customAuthenticationFailHandler;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    //@Autowired自动取得对象 满足依赖

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //将自定的CustomUserDetailsService装配到AuthenticationManagerBuilder
        //springsecurity封装的configure方法，AuthenticationManagerBuilder类（类型）throws语法 匿名对象 .语法 链式编程 重写 类与类的关系
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new CustomPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //java8新特性 链式编程 如果一个方法返回值是对象 该对象是下一个方法的参数 就可以继续往下调
        http

                /*匿名请求：不需要进行登录拦截的url*/
                .authorizeRequests()
                .antMatchers("/hello","/findALLUser","/exportGongWeiFuHe","/selectGongWeiFuHeListByDate","/deleteGongWeiFuHeById","/getLastGongWeiData","/exportGuoChenFuHe","/exportGongWeiZhiLiangShengTaiYiShi","/getGongWeiFuGaiLvSecondDataByYear","/getGongWeiFuGaiLvSecondDataByYear","/exportWenTiQinDan","/getSencondWenJianDataByDate").permitAll() //允许任何人访问
                .antMatchers("/admin").hasRole("admin")//当用户的角色是为admin时可以访问这个目录
                .antMatchers("/getUser","/a1").hasAuthority("select") //当用户具有select权限时才可以访问这个方法
                .anyRequest().authenticated()//其他的路径都是登录后才可访问
                .and()
                /*登录配置*/
                .formLogin()
                .loginPage("/login_page")//登录页，当未登录时会重定向到该页面
                .successHandler(customAuthenticationSuccessHandler)//登录成功处理
                .failureHandler(customAuthenticationFailHandler)//登录失败处理
                .loginProcessingUrl("/login")//restful登录请求地址
                .usernameParameter("username")//默认的用户名参数
                .passwordParameter("password")//默认的密码参数
                .permitAll()
                .and()
                /*登出配置*/
                .logout()
                .permitAll()
                .logoutSuccessHandler(customLogoutSuccessHandler) //退出处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)  //无权限时的处理
                .and()
                .cors() //跨域
                .and()
                //关闭csrf防护，类似于防火墙，不关闭上面的设置不会真正生效。
                .csrf().disable();
    }

    //密码加密配置
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
