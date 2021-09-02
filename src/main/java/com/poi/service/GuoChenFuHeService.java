package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.GuoChenFuHeDao;
import com.poi.polo.GuoChenFuHe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuoChenFuHeService {

    @Autowired
    private GuoChenFuHeDao guoChenFuHeDao;

    public void  addGuoChenFuHe(GuoChenFuHe guoChenFuHe){
        guoChenFuHeDao.addGuoChenFuHe(guoChenFuHe);
    };

    public List<GuoChenFuHe> selectAllGuoChenFuHe(int pageNum,int limit)throws Exception{
        PageHelper.startPage(pageNum,limit);//分页插件
        return guoChenFuHeDao.selectAllGuoChenFuHe();
    };

    public  List<GuoChenFuHe> selectGuoChenFuHeListByDate(String date, String pingShengXingZhi){
        return guoChenFuHeDao.selectGuoChenFuHeListByDate(date,pingShengXingZhi);
    }

    public  void deletGuoChenByid(int id){
        guoChenFuHeDao.deletGuoChenByid(id);
    };
}
