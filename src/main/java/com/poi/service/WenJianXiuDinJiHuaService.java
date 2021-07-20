package com.poi.service;


import com.poi.mapper.WenJianXiuDinJiHuaDao;
import com.poi.polo.WenJianXiuDinJiHua;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WenJianXiuDinJiHuaService {

    @Autowired
    private WenJianXiuDinJiHuaDao wenJianXiuDinJiHuaDao;

    public List<WenJianXiuDinJiHua> selectWenJianXiuDinJiHuaByDate(String date){
        return wenJianXiuDinJiHuaDao.selectWenJianXiuDinJiHuaByDate(date);
    };//按月份查看计划数的方法

    public void changeWenJianXiuDinJiHua(int id,String jihuashu){
        wenJianXiuDinJiHuaDao.changeWenJianXiuDinJiHua(id,jihuashu);
    }//配置计划数的方法

     public void addWenJianXiuDinjihua(String quYu,String jiHuaShu,String date){
        wenJianXiuDinJiHuaDao.addWenJianXiuDinjihua(quYu,jiHuaShu,date);
    }

    public void deletejiHua(String id){
        wenJianXiuDinJiHuaDao.deletejiHua(id);
    }
}
