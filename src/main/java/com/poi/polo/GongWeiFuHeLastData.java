package com.poi.polo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GongWeiFuHeLastData { //这是传到前端echarts的需要数据
    private String stationName;//工位名称
    private Double stationPercentage;//工位符合率
}
