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
}
