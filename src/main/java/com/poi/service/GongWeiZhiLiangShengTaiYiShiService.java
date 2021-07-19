package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.GongWeiZhiLiangShengTaiYiShiDao;
import com.poi.polo.GongWeiZhiLiangShengTaiYiShi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GongWeiZhiLiangShengTaiYiShiService {

    @Autowired
    private GongWeiZhiLiangShengTaiYiShiDao gongWeiZhiLiangShengTaiYiShiDao;

    public List<GongWeiZhiLiangShengTaiYiShi>selectALLShengTaiYiShiData(int pageNum){
        PageHelper.startPage(pageNum,10);//分页插件
      return gongWeiZhiLiangShengTaiYiShiDao.selectALLShengTaiYiShiData();
    };

    public void addShengTaiYiShiData(GongWeiZhiLiangShengTaiYiShi gongWeiZhiLiangShengTaiYiShi){
        gongWeiZhiLiangShengTaiYiShiDao.addShengTaiYiShiData(gongWeiZhiLiangShengTaiYiShi);
    }

    public List<GongWeiZhiLiangShengTaiYiShi> selectShengTaiYiShiDataByDate(String date){
      return gongWeiZhiLiangShengTaiYiShiDao.selectShengTaiYiShiDataByDate(date);
    };

    public void deleteGongWeiZhiLiangShengTaiYiShiById(int id){
        gongWeiZhiLiangShengTaiYiShiDao.deleteGongWeiZhiLiangShengTaiYiShiById(id);
    };
}
