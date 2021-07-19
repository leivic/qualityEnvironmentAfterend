package com.poi.service;


import com.github.pagehelper.PageHelper;
import com.poi.mapper.BianHuaDianDao;
import com.poi.polo.BianHuaDian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BianHuaDianService {
    @Autowired
    private BianHuaDianDao bianHuaDianDao;

    public void addBianHuaDian(BianHuaDian bianHuaDian){
        bianHuaDianDao.addBianHuaDian(bianHuaDian);
    }

    public List<BianHuaDian> selectALLBianHuaDian(int pageNum){
        PageHelper.startPage(pageNum,10);
        return bianHuaDianDao.selectALLBianHuaDian();
    }

    public List<BianHuaDian> selectBianHuaDianByDate(String date){
        return bianHuaDianDao.selectBianHuaDianByDate(date);
    }

    public void deleteBianHuaDianById(int id){
        bianHuaDianDao.deleteBianHuaDianById(id);
    }
}
