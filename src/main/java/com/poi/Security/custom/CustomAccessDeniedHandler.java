package com.poi.Security.custom;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    //自定义无权访问类 实现一个springsecurity内置的 AccessDeniedHandler接口 实现接口方法
    @Override
    //throws语句 用来方法名（参数）后面 表明这个方法可能会抛出这些异常 方法的调用者可能要处理这些异常
    //request变量是 httpservletRquest类类型的 response和accessDeniedException同理 三种类型（类）来自导入的依赖
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //HttpServletResponse.SC_FORBIDDEN代表 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //HttpServletResponse类的 setStatus和serHeader方法
        response.setHeader("Content-Type","application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("{\"status\":\"error\",\"msg\":\"权限不足！\"}");
        out.flush();
        out.close();
    }

}
