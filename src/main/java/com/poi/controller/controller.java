package com.poi.controller;


import com.poi.polo.Model3;
import com.poi.service.Model3Service;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class controller {

    @Autowired
    private Model3Service model3Service;

    @RequestMapping("/testConnect")
    public String testConnect(){

        return "服务器启动成功";
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
