package com.poi.polo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WenJianXiuDinThirdData {
    private String quYu;
    private int shuLiang;
    private String xiuGaiLeiXin; //每不同时间 不同区域 不同修改类型 的数量有多少 这是Echarts需要的数据
}
