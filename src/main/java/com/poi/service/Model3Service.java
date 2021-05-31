package com.poi.service;

import com.poi.mapper.Model3Dao;
import com.poi.polo.Model3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Model3Service{

    @Autowired
    private Model3Dao model3Dao;

    public void addModel3(Model3 model3){
        model3Dao.addModel3(model3);
    }

    public List<Model3> selectModel3ByMonth(String month){
        return model3Dao.selectModel3ByMonth(month);//这里的month是上面 int形参的month，根据上面的month传参进这个方法
    }
}
