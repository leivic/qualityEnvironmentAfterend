package com.poi.mapper;


import com.poi.polo.WenJianXiuDin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WenJianXiuDinDao {
    List<WenJianXiuDin> selectAllWenJian();//查看所有文件修订信息

    List<WenJianXiuDin> selectWenJianByMonth(String date);

    void addWenJian(WenJianXiuDin wenJianXiuDin);//添加文件

    void deleteWenJian(int id);//删除
}
