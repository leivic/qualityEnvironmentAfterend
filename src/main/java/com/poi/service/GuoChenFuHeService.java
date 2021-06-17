package com.poi.service;

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

    public List<GuoChenFuHe> selectAllGuoChenFuHe(){
        return guoChenFuHeDao.selectAllGuoChenFuHe();
    };
}
