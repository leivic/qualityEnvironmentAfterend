package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.WenTiQinDanDao;
import com.poi.polo.WenTiQinDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WenTiQinDanService {
    @Autowired
    private WenTiQinDanDao wenTiQinDanDao;

    public List<WenTiQinDan> selectALLWenTi(int pageNum){
        PageHelper.startPage(pageNum,10);
        return wenTiQinDanDao.selectALLWenTi();
    };

     public List<WenTiQinDan> selectWenTiByDate(String date,int pageNum){
         PageHelper.startPage(pageNum,10);
         return wenTiQinDanDao.selectWenTiByDate(date);
     };

     public void changeZhuangTai(String id,String zhuangtai){
         wenTiQinDanDao.changeZhuangTai(id,zhuangtai);
     }

     public void addWenTi(WenTiQinDan wenTiQinDan){
         wenTiQinDanDao.addWenTi(wenTiQinDan);
     };
}
