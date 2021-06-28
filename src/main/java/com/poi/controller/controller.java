package com.poi.controller;


import com.poi.Security.custom.CustomUserDetailsService;
import com.poi.mapper.UserDao;
import com.poi.polo.*;
import com.poi.service.*;
import com.poi.util.MultiPartFileUtil;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.poi.Security.utils.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private GongWeiFuHeService gongWeiFuHeService;

    @Autowired
    private GuoChenFuHeService guoChenFuHeService;

    @Autowired
    private GongWeiZhiLiangShengTaiYiShiService gongWeiZhiLiangShengTaiYiShiService;

    @Autowired
    private BianHuaDianService bianHuaDianService;

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

    @PostMapping("exportGongWeiFuHe")
    public void exportGongWeiFuHe(@RequestParam("file") MultipartFile file) throws IOException{
        FileInputStream fns=(FileInputStream)file.getInputStream();
        XSSFWorkbook wb=new XSSFWorkbook(fns);//xssWorkbook少了hssworkbook的解析成 POIFSFileSystem数据类型这一步
        XSSFSheet sheetAt = wb.getSheetAt(0);
        if(sheetAt==null) {
            return;
        }

        //由此得到 应该创建一个多大的对象数组
        int number =0;
        for(int rowIndex=0;rowIndex<sheetAt.getLastRowNum();rowIndex++){
            XSSFCell reviewCell = sheetAt.getRow(rowIndex).getCell(20);
            if (reviewCell.toString().equals("工位")){////循环第20列  如果第二十列是工位 再判断后面是过程符合率 还是工位符合率 此处注意.equal()和==的区别
                XSSFCell cell = sheetAt.getRow(rowIndex).getCell(28);//28cell 适用条款数
                if(cell.toString() !=""){
                    number ++;
                    System.out.println(number);//由此得到 应该创建一个多大的对象数组
                }
            }
            }

        GongWeiFuHe[] gongWeiFuHes =new GongWeiFuHe[number];//创建对象数组
        for(int rowIndex=0,num = 0;rowIndex<sheetAt.getLastRowNum();rowIndex++){
            XSSFCell reviewCell = sheetAt.getRow(rowIndex).getCell(20);
            if (reviewCell.toString().equals("工位")){////循环第20列  如果第二十列是工位 再判断后面是过程符合率 还是工位符合率 此处注意.equal()和==的区别
                XSSFCell cell = sheetAt.getRow(rowIndex).getCell(28);//28cell 适用条款数
                if(cell.toString() !=""){//在此处判断   当处于这种情况下 这一行都是工位数据 下面对对象数组里对对象进行赋值
                    XSSFRow rowGongWei=sheetAt.getRow(rowIndex);//取当前行
                    gongWeiFuHes[num]=new GongWeiFuHe();
                    gongWeiFuHes[num].setAssignMentid(rowGongWei.getCell(9).toString());
                    gongWeiFuHes[num].setWorkModel(rowGongWei.getCell(10).toString());
                    gongWeiFuHes[num].setItemDescription(rowGongWei.getCell(11).toString());
                    gongWeiFuHes[num].setReview(rowGongWei.getCell(20).toString());
                    gongWeiFuHes[num].setStationName(rowGongWei.getCell(27).toString());
                    gongWeiFuHes[num].setApplicableTerms(rowGongWei.getCell(28).toString());
                    gongWeiFuHes[num].setMeetTheTerms(rowGongWei.getCell(29).toString());
                    gongWeiFuHes[num].setStationPercentage(rowGongWei.getCell(30).getNumericCellValue());//
                    gongWeiFuHes[num].setAuditQuestions(rowGongWei.getCell(31).toString());
                    gongWeiFuHes[num].setPinShenQuYu(rowGongWei.getCell(32).toString());
                    gongWeiFuHes[num].setPinShenShiJian(rowGongWei.getCell(33).toString());
                    gongWeiFuHes[num].setYinShenRenYuan(rowGongWei.getCell(34).toString());
                    gongWeiFuHes[num].setZiPingORChouCha(rowGongWei.getCell(71).toString());//评审性质字段 后面添加
                    System.out.println(gongWeiFuHes[num]);//此时已获得对象 获得了gongWeiFuHeService的 .addGongWeiFuHe方法需要的参数
                    gongWeiFuHeService.addGongWeiFuHe(gongWeiFuHes[num]);

                }
            }
        }
    }

    @ResponseBody
    @RequestMapping("/selectAllGongWeiFuHe")
    List<GongWeiFuHe> selectAllGongWeiFuHe(Integer pageNum)throws Exception{
        return gongWeiFuHeService.selectAllGongWeiFuHe(pageNum.intValue());
    }

    @RequestMapping("/selectGongWeiFuHeListByDate")
    @ResponseBody
    List<GongWeiFuHe> selectGongWeiFuHeListByDate(String date,String pingShengXingZhi){
        return gongWeiFuHeService.selectGongWeiFuHeListByDate(date,pingShengXingZhi);
    }

    @ResponseBody
    @RequestMapping("/getLastGongWeiData")
    GongWeiFuHeLastData[] getLastGongWeiData(String date,String pingShengXingZhi){ //返回一个对象数组
        System.out.println(gongWeiFuHeService.selectGongWeiFuHeListByDate(date,pingShengXingZhi));
        List<GongWeiFuHe> test=gongWeiFuHeService.selectGongWeiFuHeListByDate(date,pingShengXingZhi);
        //解决思路 1.先取出每一项工位＋区域 然后去重得到一个数组 2.以工位＋区域循环数组每一项  取出相等时的所有条目  算平均值 然后与把平均值加入一个list

        List<String> stationName=test.stream().map(GongWeiFuHe::getStationName).distinct().collect(Collectors.toList());//java8 stream特性 操作集合取出StationName 并去重
        System.out.println(stationName);

        GongWeiFuHeLastData[] gongWeiFuHeLastData=new GongWeiFuHeLastData[stationName.size()];//新建一个对象数组 这是返回给echarts的对象数组

        for(int i=0,n=stationName.size();i<n;i++){   //循环每一个工位
            final int d=i;
           List<GongWeiFuHe> filterStation=test.stream().filter(gongWeiFuHe -> gongWeiFuHe.getStationName().equals(stationName.get(d))).collect(Collectors.toList());//过滤出和工位名称相等的list集合
           System.out.println(filterStation);
           Double getStationPercentage=(filterStation.stream().filter(gongWeiFuHe ->
           gongWeiFuHe.getStationPercentage()!=null).mapToDouble((GongWeiFuHe::getStationPercentage)))
                   .average().orElse(0D);
           System.out.println(getStationPercentage);//得到循环出来分别有每个工位的集合 再分别算平均值

           GongWeiFuHeLastData gongWeiFuHeLastData1=new GongWeiFuHeLastData();//新建对象 现在已经得到了每个工位和对应的 平均值 要做的是把他们加入对象数组内 返回给前端
            gongWeiFuHeLastData1.setStationName(stationName.get(i));
            gongWeiFuHeLastData1.setStationPercentage(getStationPercentage);//
            gongWeiFuHeLastData[i]=gongWeiFuHeLastData1;
            System.out.println(gongWeiFuHeLastData1);
        }

        return gongWeiFuHeLastData;

    }

    @RequestMapping("/deleteGongWeiFuHeById")
    @ResponseBody
    public void deleteGongWeiFuHeById(int id){
        gongWeiFuHeService.deleteGongWeiFuHeById(id);
    };

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

    @PostMapping("/exportGuoChenFuHe")
    public void exportGuoChenFuHe(@RequestParam("file") MultipartFile file) throws IOException{
        FileInputStream fns=(FileInputStream)file.getInputStream();
        XSSFWorkbook wb=new XSSFWorkbook(fns);//xssWorkbook少了hssworkbook的解析成 POIFSFileSystem数据类型这一步
        XSSFSheet sheetAt = wb.getSheetAt(0);
        if(sheetAt==null) {
            return;
        }

        //由此得到 应该创建一个多大的对象数组
        int number =0;
        for(int rowIndex=0;rowIndex<sheetAt.getLastRowNum();rowIndex++){
            XSSFCell reviewCell = sheetAt.getRow(rowIndex).getCell(20);
            if (reviewCell.toString().equals("工位")){////循环第20列  如果第二十列是工位 再判断后面是过程符合率 还是工位符合率 此处注意.equal()和==的区别
                XSSFCell cell = sheetAt.getRow(rowIndex).getCell(38);//第39列过程名称有值 则number＋＋
                if(cell.toString() !=""){
                    number ++;
                    System.out.println(number);//由此得到 应该创建一个多大的对象数
                }
            }
        }


        GuoChenFuHe[] guoChenFuHes =new GuoChenFuHe[number];//创建对象数组
        for(int rowIndex=0,num = 0;rowIndex<sheetAt.getLastRowNum();rowIndex++){
            XSSFCell reviewCell = sheetAt.getRow(rowIndex).getCell(20);
            if (reviewCell.toString().equals("工位")){////循环第20列  如果第二十列是工位 再判断后面是过程符合率 还是工位符合率 此处注意.equal()和==的区别
                XSSFCell cell = sheetAt.getRow(rowIndex).getCell(38);
                if(cell.toString() !=""){//在此处判断   当处于这种情况下 这一行都是工位数据 下面对对象数组里对对象进行赋值
                    XSSFRow rowGongWei=sheetAt.getRow(rowIndex);//取当前行
                    guoChenFuHes[num]=new GuoChenFuHe();
                    guoChenFuHes[num].setAssignMentid(rowGongWei.getCell(9).toString());
                    guoChenFuHes[num].setWorkModel(rowGongWei.getCell(10).toString());
                    guoChenFuHes[num].setItemDescription(rowGongWei.getCell(11).toString());
                    guoChenFuHes[num].setReview(rowGongWei.getCell(20).toString());
                    guoChenFuHes[num].setGuoChenMinChen(rowGongWei.getCell(38).toString());
                    guoChenFuHes[num].setGuoChenPeiFen(rowGongWei.getCell(39).toString());
                    guoChenFuHes[num].setGuoChenDeFen(rowGongWei.getCell(40).toString());
                    guoChenFuHes[num].setGuoChenPercentage(rowGongWei.getCell(41).getNumericCellValue());//
                    guoChenFuHes[num].setAuditQuestions(rowGongWei.getCell(42).toString());
                    guoChenFuHes[num].setPinShenQuYu(rowGongWei.getCell(35).toString());
                    guoChenFuHes[num].setPinShenShiJian(rowGongWei.getCell(36).toString());
                    guoChenFuHes[num].setYinShenRenYuan(rowGongWei.getCell(37).toString());
                    guoChenFuHes[num].setZiPinOrChouCha(rowGongWei.getCell(71).toString());//评审性质字段 后面添加
                    System.out.println(guoChenFuHes[num]);//此时已获得对象 获得了gongWeiFuHeService的 .addGongWeiFuHe方法需要的参数
                    guoChenFuHeService.addGuoChenFuHe(guoChenFuHes[num]);

                }
            }
        }
    }

    @ResponseBody
    @RequestMapping("/selectAllGuoChenFuHe")
    public List<GuoChenFuHe> selectAllGuoChenFuHe(Integer pageNum)throws Exception{

        return guoChenFuHeService.selectAllGuoChenFuHe(pageNum.intValue());
    };

    @ResponseBody
    @RequestMapping("/GetSecondGuoChenDataByQuYu") //按评审区域字段计算得分率平均值
    GuoChenFuHeSecondData[] GetSecondGuoChenDataByQuYu(String date,String pingShengXingZhi){ //返回一个对象数组
        System.out.println(guoChenFuHeService.selectGuoChenFuHeListByDate(date,pingShengXingZhi));
        List<GuoChenFuHe> test=guoChenFuHeService.selectGuoChenFuHeListByDate(date,pingShengXingZhi);


        List<String> quYuMinChen=test.stream().map(GuoChenFuHe::getPinShenQuYu).distinct().collect(Collectors.toList());
        System.out.println(quYuMinChen);

        GuoChenFuHeSecondData[] guoChenFuHeSecondData=new GuoChenFuHeSecondData[quYuMinChen.size()];

        for(int i=0,n=quYuMinChen.size();i<n;i++){
            final int d=i;
            List<GuoChenFuHe> filterGuoChen=test.stream().filter(guoChenFuHe -> guoChenFuHe.getPinShenQuYu().equals(quYuMinChen.get(d))).collect(Collectors.toList());//过滤出和过程名称相等的list集合
            System.out.println(filterGuoChen);
            Double getGuoChenPercentage=(filterGuoChen.stream().filter(guoChenFuHe ->
                    guoChenFuHe.getGuoChenPercentage()!=null).mapToDouble((GuoChenFuHe::getGuoChenPercentage)))
                    .average().orElse(0D);
            System.out.println(getGuoChenPercentage);

            GuoChenFuHeSecondData guoChenFuHeSecondData1=new GuoChenFuHeSecondData();
            guoChenFuHeSecondData1.setFenLeiYiJu(quYuMinChen.get(i));
            guoChenFuHeSecondData1.setGuoChenpercentage(getGuoChenPercentage);
            guoChenFuHeSecondData[i]=guoChenFuHeSecondData1;
            System.out.println(guoChenFuHeSecondData1);
        }

        return guoChenFuHeSecondData;

    }



    @ResponseBody
    @RequestMapping("/GetSecondGuoChenDataByGuoChen") //按过程字段计算得分率平均值
    GuoChenFuHeSecondData[] GetSecondGuoChenDataByGuoChen(String date,String pingShengXingZhi){ //返回一个对象数组
        System.out.println(guoChenFuHeService.selectGuoChenFuHeListByDate(date,pingShengXingZhi));
        List<GuoChenFuHe> test=guoChenFuHeService.selectGuoChenFuHeListByDate(date,pingShengXingZhi);


        List<String> guoChenMinChen=test.stream().map(GuoChenFuHe::getGuoChenMinChen).distinct().collect(Collectors.toList());
        System.out.println(guoChenMinChen);

        GuoChenFuHeSecondData[] guoChenFuHeSecondData=new GuoChenFuHeSecondData[guoChenMinChen.size()];

        for(int i=0,n=guoChenMinChen.size();i<n;i++){
            final int d=i;
            List<GuoChenFuHe> filterGuoChen=test.stream().filter(guoChenFuHe -> guoChenFuHe.getGuoChenMinChen().equals(guoChenMinChen.get(d))).collect(Collectors.toList());//过滤出和过程名称相等的list集合
            System.out.println(filterGuoChen);
            Double getGuoChenPercentage=(filterGuoChen.stream().filter(guoChenFuHe ->
                    guoChenFuHe.getGuoChenPercentage()!=null).mapToDouble((GuoChenFuHe::getGuoChenPercentage)))
                    .average().orElse(0D);
            System.out.println(getGuoChenPercentage);

            GuoChenFuHeSecondData guoChenFuHeSecondData1=new GuoChenFuHeSecondData();
            guoChenFuHeSecondData1.setFenLeiYiJu(guoChenMinChen.get(i));
            guoChenFuHeSecondData1.setGuoChenpercentage(getGuoChenPercentage);
            guoChenFuHeSecondData[i]=guoChenFuHeSecondData1;
            System.out.println(guoChenFuHeSecondData1);
        }

        return guoChenFuHeSecondData;

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
        System.out.println(month.get("month"));//获取键值对map month中 键为month的值
        return model3Service.selectModel3ByMonth(month.get("month"));//month.get（"month"）是指 取得键为month的值，是string类型的
    }


    @PostMapping("/exportGongWeiZhiLiangShengTaiYiShi")
     public void exportGongWeiZhiLiangShengTaiYiShi(@RequestParam("file") MultipartFile file)throws IOException{
         FileInputStream fns=(FileInputStream)file.getInputStream();
         XSSFWorkbook wb=new XSSFWorkbook(fns);//xssWorkbook少了hssworkbook的解析成 POIFSFileSystem数据类型这一步
         XSSFSheet sheetAt = wb.getSheetAt(0);
         if(sheetAt==null) {
             return;
         }

         //由此得到 应该创建一个多大的对象数组

         for(int rowIndex=0,num = 0;rowIndex<sheetAt.getLastRowNum();rowIndex++){
             XSSFCell cell = sheetAt.getRow(rowIndex).getCell(52);//先循环每一行 看区域的那个单元格不为空 再取当前这行做处理
             if (cell.toString().length()>2){ //小于2截取字符串会报错
                 if(cell.toString().substring(cell.toString().length()-2,cell.toString().length()).equals("车间")){//如果字符串最后两位＝"车间"
                     XSSFRow rowZhiLiangShengTaiYiShi=sheetAt.getRow(rowIndex);//取当前行
                     GongWeiZhiLiangShengTaiYiShi gongWeiZhiLiangShengTaiYiShi=new GongWeiZhiLiangShengTaiYiShi();
                     gongWeiZhiLiangShengTaiYiShi.setGongWeiHao(rowZhiLiangShengTaiYiShi.getCell(53).toString());//工位号
                     gongWeiZhiLiangShengTaiYiShi.setShenHeQuYu(rowZhiLiangShengTaiYiShi.getCell(52).toString());
                     gongWeiZhiLiangShengTaiYiShi.setPinJiaRiQi(rowZhiLiangShengTaiYiShi.getCell(51).toString());
                     gongWeiZhiLiangShengTaiYiShi.setDiXianYiShi(rowZhiLiangShengTaiYiShi.getCell(54).toString());
                     gongWeiZhiLiangShengTaiYiShi.setZeRenYiShi(rowZhiLiangShengTaiYiShi.getCell(55).toString());
                     gongWeiZhiLiangShengTaiYiShi.setZhuRenWengYiShi(rowZhiLiangShengTaiYiShi.getCell(56).toString());
                     gongWeiZhiLiangShengTaiYiShi.setGaiJinYiShi(rowZhiLiangShengTaiYiShi.getCell(57).toString());
                     gongWeiZhiLiangShengTaiYiShi.setJiGeFen("64");
                     gongWeiZhiLiangShengTaiYiShi.setZongFen("80");
                     gongWeiZhiLiangShengTaiYiShiService.addShengTaiYiShiData(gongWeiZhiLiangShengTaiYiShi);//添加进数据库
                 }
             }

         }

    }

    @ResponseBody
    @RequestMapping("/selectALLShengTaiYiShiData")
    List<GongWeiZhiLiangShengTaiYiShi>selectALLShengTaiYiShiData(int pageNum){
        return gongWeiZhiLiangShengTaiYiShiService.selectALLShengTaiYiShiData(pageNum);
    }

    @ResponseBody
    @RequestMapping("/selectShengTaiYiShiDataByDate")
    List<GongWeiZhiLiangShengTaiYiShi>selectShengTaiYiShiDataByDate(String date){
        return gongWeiZhiLiangShengTaiYiShiService.selectShengTaiYiShiDataByDate(date);
    }

    @RequestMapping("/deleteGongWeiZhiLiangShengTaiYiShiById")
    void deleteGongWeiZhiLiangShengTaiYiShiById(int id){
        gongWeiZhiLiangShengTaiYiShiService.deleteGongWeiZhiLiangShengTaiYiShiById(id);
    }

    @PostMapping("/exportBianHuaDian")
    void exportBianHuaDian(@RequestParam("file") MultipartFile file) throws IOException{
        FileInputStream fns=(FileInputStream)file.getInputStream();
        XSSFWorkbook wb=new XSSFWorkbook(fns);//xssWorkbook少了hssworkbook的解析成 POIFSFileSystem数据类型这一步
        XSSFSheet sheetAt = wb.getSheetAt(0);
        if(sheetAt==null) {
            return;
        }

        for(int rowIndex=0,num = 0;rowIndex<sheetAt.getLastRowNum();rowIndex++){    //循环每一行
            XSSFCell cell = sheetAt.getRow(rowIndex).getCell(60);
            if(cell.toString().length()==1){  //人 机 料 法  环
                BianHuaDian bianHuaDian=new BianHuaDian();
                bianHuaDian.setGongWeiMinChen(sheetAt.getRow(rowIndex).getCell(59).toString());
                bianHuaDian.setShenHeRiQi(sheetAt.getRow(rowIndex).getCell(58).toString());
                bianHuaDian.setShenHeQuYu(sheetAt.getRow(rowIndex).getCell(64).toString());
                bianHuaDian.setLeiXin(sheetAt.getRow(rowIndex).getCell(60).toString());
                bianHuaDian.setBianHuaDianLeiRong(sheetAt.getRow(rowIndex).getCell(61).toString());
                bianHuaDian.setOkOrNoOk(sheetAt.getRow(rowIndex).getCell(63).toString());
                bianHuaDianService.addBianHuaDian(bianHuaDian);//将赋值好的对象 作为参数传入bianHuaDianService 的addBianHuaDian 方法
            }

        }

    }

    @ResponseBody
    @RequestMapping("/selectALLBianHuaDian")
    List<BianHuaDian> selectALLBianHuaDian(int pageNum){
        return bianHuaDianService.selectALLBianHuaDian(pageNum);
    }

    @ResponseBody
    @RequestMapping("/selectBianHuaDianByDate")
    List<BianHuaDian> selectBianHuaDianByDate(String date){
        return bianHuaDianService.selectBianHuaDianByDate(date);
    }

    @RequestMapping("/deleteBianHuaDianById")
    void deleteBianHuaDianById(int id){
        bianHuaDianService.deleteBianHuaDianById(id);
    }

    }
