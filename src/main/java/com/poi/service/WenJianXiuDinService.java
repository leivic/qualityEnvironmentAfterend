package com.poi.service;

import com.github.pagehelper.PageHelper;
import com.poi.mapper.WenJianXiuDinDao;
import com.poi.polo.WenJianXiuDin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WenJianXiuDinService {

    @Autowired
    private WenJianXiuDinDao wenJianXiuDinDao;

    public void addWenJian(WenJianXiuDin wenJianXiuDin){
        wenJianXiuDinDao.addWenJian(wenJianXiuDin);
    };

    public List<WenJianXiuDin> selectAllWenJian(int pageNum){
        PageHelper.startPage(pageNum,10);
        return wenJianXiuDinDao.selectAllWenJian();
    };

    public List<WenJianXiuDin> selectWenJianByMonth(String date){
        return wenJianXiuDinDao.selectWenJianByMonth(date);
    };

    public void deleteWenJian(int id){
        wenJianXiuDinDao.deleteWenJian(id);
    };
}
