package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.GongWeiFuHeDao;
import com.poi.polo.GongWeiFuHe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GongWeiFuHeService {
    @Autowired
    private GongWeiFuHeDao gongWeiFuHeDao;//GongWeiFuHeDao只是个接口 但是写mybatis提供的xml 就相当于写实体类了

    public void addGongWeiFuHe(GongWeiFuHe gongWeiFuHe){
        gongWeiFuHeDao.addGongWeiFuHe(gongWeiFuHe);//传一个gongweifuhe类型的数据进来
    };

    public List<GongWeiFuHe> selectGongWeiFuHeListByDate(String date,String pingShengXingZhi){
        return gongWeiFuHeDao.selectGongWeiFuHeListByDate(date,pingShengXingZhi);
    };

    public void deleteGongWeiFuHeById(int id){
        gongWeiFuHeDao.deleteGongWeiFuHeById(id);
    };

    public List<GongWeiFuHe> selectAllGongWeiFuHe(int pageNum)throws Exception{
        PageHelper.startPage(pageNum,10);//分页插件
        return gongWeiFuHeDao.selectAllGongWeiFuHe();
    };
}
