package com.poi.polo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GongWeiFuHe {
    private int id;
    private String assignMentid;// 任务书编号  同时根据此字段得出时间
    private String workModel;//工作模块
    private String itemDescription;//项目描述
    private String ziPingORChouCha;//根据此字段判断是自评还是抽查
    private String review;//评审内容
    private String stationName;//工位名称
    private String applicableTerms;//适用条款数
    private String meetTheTerms;//符合条款数
    private Double stationPercentage;//工位符合率
    private String auditQuestions;//工位审核问题数
    private String pinShenQuYu;//评审区域
    private String pinShenShiJian;//评审时间
    private String yinShenRenYuan;//迎审人员
}
