package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.GongWeiFuGaiLvDao;
import com.poi.polo.GongWeiFuGaiLv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GongWeiFuGaiLvService {

    @Autowired
    private GongWeiFuGaiLvDao gongWeiFuGaiLvDao;

    public void addFuGaiLV(GongWeiFuGaiLv gongWeiFuGaiLv){//不加public controller就读取不到  无重复工位的插入工位符合率数据
        gongWeiFuGaiLvDao.addFuGaiLV(gongWeiFuGaiLv);
    }

    public List<GongWeiFuGaiLv> selectAllGongWeiFuGaiLv(int pageNum){
        PageHelper.startPage(pageNum,8);
        return gongWeiFuGaiLvDao.selectAllGongWeiFuGaiLv();
    };

    public List<GongWeiFuGaiLv> selectGongWeiFuGaiLvByYear(String year){
        return gongWeiFuGaiLvDao.selectGongWeiFuGaiLvByYear(year);//按年份选出该年的 不会重复的所有数据
    };

    public int selectGongWeiShuByYueFen(String year,String yueFen){
        return gongWeiFuGaiLvDao.selectGongWeiShuByYueFen(year,yueFen);
    }
}
