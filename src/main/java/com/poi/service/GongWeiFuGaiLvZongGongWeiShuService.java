package com.poi.service;

import com.poi.mapper.GongWeiFuGaiLvZongGongWeiShuDao;
import com.poi.polo.GongWeiFuGaiLVZongGongWeiShu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GongWeiFuGaiLvZongGongWeiShuService {
    @Autowired
    private GongWeiFuGaiLvZongGongWeiShuDao gongWeiFuGaiLvZongGongWeiShuDao;

    public List<GongWeiFuGaiLVZongGongWeiShu> selecAll(){
        return gongWeiFuGaiLvZongGongWeiShuDao.selectAll();
    }

    public List<GongWeiFuGaiLVZongGongWeiShu> selectByQuYu(String quYu){
        return  gongWeiFuGaiLvZongGongWeiShuDao.selectByQuYu(quYu);
    }

    public void change(String quYu,String shuliang){
        gongWeiFuGaiLvZongGongWeiShuDao.change(quYu,shuliang);//根据区域 改变到传入的数量
    }
}
