package com.poi.controller;


import com.poi.Security.custom.CustomUserDetailsService;
import com.poi.mapper.UserDao;
import com.poi.polo.Model1;
import com.poi.polo.Model3;
import com.poi.polo.User;
import com.poi.service.Model1Service;
import com.poi.service.Model3Service;
import com.poi.service.PermissionService;
import com.poi.service.UserService;
import com.poi.util.MultiPartFileUtil;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.poi.Security.utils.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class controller {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private Model3Service model3Service;

    @Autowired
    private Model1Service model1Service;

    @RequestMapping("/testConnect")
    public String testConnect(){

        return "服务器启动成功";
    }


    @RequestMapping("/hello")
    @ResponseBody
    public Response hello(){
        return new Response("200","hello!");
    }

    //该方法我们在security配置类中指定了admin角色才可以访问
    //当然也可以直接添加@PreAuthorize("hasRole('admin')")
    @RequestMapping("/admin")
    @ResponseBody
    public Response admin(){
        return new Response("200","admin!");
    }//return new Response 是在生成匿名对象
    //当用户具有select权限时才可以访问该方法
    @PreAuthorize("hasAuthority('select')")//springsercurity封装的注解
    @RequestMapping("/select")
    @ResponseBody
    public Response select(){
        return new Response("200","select");
    }
    //当用户具有insert权限时才可以访问该方法
    @PreAuthorize("hasAuthority('insert')")
    @RequestMapping("/insert")
    @ResponseBody
    public Response insert(){
        return new Response("200","insert");
    }
    //当用户具有update权限时才可以访问该方法
    @PreAuthorize("hasAuthority('update')")
    @RequestMapping("/update")
    @ResponseBody
    public Response update(){
        return new Response("200","update");
    }
    //如果访问需要登录的接口，如果用户还没登录就会跳转到这个接口
    @RequestMapping("/login_page")
    @ResponseBody
    public Response root(){
        Response response = new Response("-200","未登录！");
        return response;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(){
        //获取我们正在登陆的用户信息
        //注意这里的User是security的
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //这里的User才是我们实体类里面的
        User user = userService.findByName(userDetails.getUsername());
        return user;
    }

    @ResponseBody
    @RequestMapping("/findALLUser")
    public List<Map<String,Object>> findALLUser(int pageNum)throws Exception{ //这样的参数 ajax直接params传就行了
        return userService.findAllUser(pageNum);//pagesize10已经在service里面写死
    }
    @ResponseBody
    @RequestMapping("/a1")
    private UserDetails getDetails(){
        //获取正在登录的详细信息 前端根据该方法返回信息判断 页面跳转和显现等
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }


    @PostMapping("/exportTestPoi")
    @SneakyThrows
    public void exportTestPoi(@RequestParam("file") MultipartFile file) throws IOException{//@RequestParam是将请求参数 绑定到方法参数上
        //请求参数file和我的方法参数file对应了起来 MultipartFile是spring封装的一个类 返回MultipartFile类型的对象
        //MultipartFile一般接受前台传来的文件 前端中键值对形式上传的 文件本质是二进制数据＋文件名称

        FileInputStream fns=(FileInputStream)file.getInputStream();//required FileInputStream found:InputStream  等式左侧与右侧数据类型不同 所以此处加上强转符
        //fns是FileInputStream输入流类型的数据 file.getInputStream()获得的是InputStream类型的数据 所以需要强转
        POIFSFileSystem pfs=new POIFSFileSystem(fns);//poi包装的类 接收一个输入流
        HSSFWorkbook wb=new HSSFWorkbook(pfs);//poi包装的类 由pfs获取工作簿
        HSSFSheet sheetAt = wb.getSheetAt(0);//获取wb的第一个sheet页
        if(sheetAt==null) {
            return;
        }//如果没有sheet页就return
        for(int rowIndex=0;rowIndex<=sheetAt.getLastRowNum();rowIndex++){ //循环获取第一个sheet的每一行 这里的sheet是在java里的抽象数据 java里抽象的sheet对应一个真正的
            //excel表的真正sheet  这就是具象概念在java中的抽象表现
        HSSFRow hssfRow = sheetAt.getRow(rowIndex);//获取到当前行的对象
            if(hssfRow==null){
                continue;
            }


            for(int cellNum=0;cellNum<=hssfRow.getLastCellNum();cellNum++){//循环获取每一行的每一个表格
                HSSFCell hssfCell=hssfRow.getCell(cellNum);//循环获取每一个表格对象
                if(hssfCell==null){
                    continue;
                }

                System.out.print(" "+hssfCell.getStringCellValue());

            }



        }
    }

    @PostMapping("/exportModel1")
    public void exportModel1(@RequestParam("file") MultipartFile file) throws IOException{
        String year="2021";//虚构一个变量 本来该变量是参数传入的 这里先虚构数据
        String month="4";//虚构一个月份

        FileInputStream fns=(FileInputStream)file.getInputStream();
        POIFSFileSystem pfs=new POIFSFileSystem(fns);
        HSSFWorkbook wb=new HSSFWorkbook(pfs);
        HSSFSheet sheetAt = wb.getSheetAt(0);//抽象意义上获得导入excel的第一个sheet 该sheet是HSSFSheet类型的
        if(sheetAt==null) {
            return;
        }
        HSSFCell Row3Col1=sheetAt.getRow(2).getCell(0);//链式编程  当sheeAt.getRow得到的对象 是拥有getCell方法类型的对象时 可以继续.
        //获得车间的值
        System.out.println(Row3Col1.toString());

        int maxAmount = (sheetAt.getLastRowNum()-2)*((sheetAt.getRow(2).getLastCellNum()-1)/4);
        //根据传入excel格式的不同，调用poi封装的方法，来判断对象数组共有多少个对象 由于上式值类型都是int 所以可以进行运算 值类型很重要
        Model1[] model1s=new Model1[maxAmount];
        System.out.println("对象数组最大可存"+maxAmount+"个对象");

        for(int rowIndex=2;rowIndex<=sheetAt.getLastRowNum()-1;rowIndex++){//首先循环每行 从第三行开始 不到最后一行，以此来获得不同的工段
            HSSFCell cellWorkshop = sheetAt.getRow(rowIndex).getCell(1);//获得循环的每一行的工段的那个单元格
            System.out.println("工段的单元格包括"+cellWorkshop.toString()+"工段");
            for (int colIndex=2;colIndex<=sheetAt.getRow(2).getLastCellNum();colIndex++){//循环每一行的后面每一个单元格
                HSSFCell cell = sheetAt.getRow(rowIndex).getCell(colIndex);//抽象的cell就是我在循环的每一个单元格

            }
        }

    }

    @PostMapping("/exportModel3")
    @SneakyThrows
    public void exportModel3(@RequestParam("file") MultipartFile file) throws IOException{//@RequestParam是将请求参数 绑定到方法参数上
        //请求参数file和我的方法参数file对应了起来 MultipartFile是spring封装的一个类 返回MultipartFile类型的对象
        //MultipartFile一般接受前台传来的文件 前端中键值对形式上传的 文件本质是二进制数据＋文件名称

        FileInputStream fns=(FileInputStream)file.getInputStream();//required FileInputStream found:InputStream  等式左侧与右侧数据类型不同 所以此处加上强转符
        //fns是FileInputStream输入流类型的数据 file.getInputStream()获得的是InputStream类型的数据 所以需要强转
        POIFSFileSystem pfs=new POIFSFileSystem(fns);//poi包装的类 接收一个输入流
        HSSFWorkbook wb=new HSSFWorkbook(pfs);//poi包装的类 由pfs获取工作簿
        HSSFSheet sheetAt = wb.getSheetAt(0);//获取wb的第一个sheet页
        if(sheetAt==null) {
            return;
        }//如果没有sheet页就return

        HSSFRow Row1 = sheetAt.getRow(1);//从0开始算 所以第二行rowindex是1
        HSSFRow rowStation = sheetAt.getRow(2);//工位那行
        HSSFRow rowPercentage = sheetAt.getRow(sheetAt.getLastRowNum()-1);//百分比那行
        HSSFCell Row1Col2=Row1.getCell(2);//获取第2行第2个单元格对象
        HSSFCell Row1Col13=Row1.getCell(13);//获取第2行第4个单元格对象

        Model3[] model3s=new Model3[rowStation.getLastCellNum()-4];//创建对象数组 rowStation.getLastCellNum()-4算出来是int类型的 所以可以作为参数传递进去
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        for(int cellNum=2;cellNum<=rowStation.getLastCellNum()-3;cellNum++){
            HSSFCell hssfCell=rowStation.getCell(cellNum);
            HSSFCell hssfCell1=rowPercentage.getCell(cellNum);
            System.out.println(hssfCell.toString());
            System.out.println(evaluator.evaluate(hssfCell1).getNumberValue());
            model3s[cellNum-2]=new Model3();//Model3[] model3s=new Model3[rowStation.getLastCellNum()-4]仅是创建对象数组，数组里面的每个对象还没创建
            model3s[cellNum-2].setDepartment(Row1Col2.getStringCellValue());//给对象数组的第一个对象的部门赋值
            model3s[cellNum-2].setMonth(Row1Col13.toString());//给对象数组的第一个对象的月份赋值
            model3s[cellNum-2].setStation(hssfCell.toString());
            model3s[cellNum-2].setPercentage(evaluator.evaluate(hssfCell1).getNumberValue());//给对象数组的百分比赋值，double类型
            model3Service.addModel3(model3s[cellNum-2]);//上面已经将对象里面的属性赋值，将对象传参入service的方法，添加数据进数据库
        }


        System.out.println(Row1Col2.getStringCellValue()+","+Row1Col13.getStringCellValue());


        }
    @PostMapping("/selectModel3ByMonth")
    public List<Model3> selectModel3ByMonth(@RequestBody Map<String, String> month){//这里的month是map类型的 键值对类型
        System.out.println(month.get("month"));
        return model3Service.selectModel3ByMonth(month.get("month"));//month.get（"month"）是指 取得键为month的值，是string类型的
    }

    }
