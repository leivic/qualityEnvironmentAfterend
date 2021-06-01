package com.poi.util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class MultiPartFileUtil {
    //这个方法写来整合这一段重复代码，需要用到这段代码的时候，只要调用这个类的这个方法即可
    public void multipartFileUtil1(@RequestParam("file") MultipartFile file) throws IOException {
        FileInputStream fns=(FileInputStream)file.getInputStream();//required FileInputStream found:InputStream  等式左侧与右侧数据类型不同 所以此处加上强转符
        //fns是FileInputStream输入流类型的数据 file.getInputStream()获得的是InputStream类型的数据 所以需要强转
        POIFSFileSystem pfs=new POIFSFileSystem(fns);//poi包装的类 接收一个输入流
        HSSFWorkbook wb=new HSSFWorkbook(pfs);//poi包装的类 由pfs获取工作簿
        HSSFSheet sheetAt = wb.getSheetAt(0);//获取wb的第一个sheet页
        if(sheetAt==null) {
            return;
        }//如果没有sheet页就return   //本意是实现重复代码的封装 但是这是个错误的例子 因为变量的作用域只在一个大括号内，是传不到调用这段代码的地方的
    }
}
