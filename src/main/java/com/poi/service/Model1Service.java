package com.poi.service;

import com.poi.mapper.Model1Dao;
import com.poi.polo.Model1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Model1Service {

    @Autowired
    private Model1Dao model1Dao;

    public void addModel1(Model1 model1){
        model1Dao.addModel1(model1);
    };

    public List<Model1> selectModel1ByDate(String Date){
        return model1Dao.selectModel1ByDate(Date);
    };//根据日期 查出一个以Model1对象为元素的集合

    public void deleteModel1ByDate(String Date){
        model1Dao.deleteModel1ByDate(Date);
    }
}
