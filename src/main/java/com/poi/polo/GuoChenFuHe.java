package com.poi.polo;

//思路 每次基础数据处理完后  要处理成二级数据存起来  同时一级数据处要有生成按钮 点击直接生成二级数据（二级数据清空重新存入 免得出错）=>
//是为了最后的质量信用积分等数据 不要处理那么多层

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuoChenFuHe {
    private int id;
    private String assignMentid;
    private String workModel;
    private String itemDescription;
    private String ziPinOrChouCha;
    private String review;
    private String guoChenMinChen;
    private String guoChenPeiFen;
    private String guoChenDeFen;
    private Double guoChenPercentage;
    private String auditQuestions;
    private String pinShenQuYu;
    private String pinShenShiJian;
    private String yinShenRenYuan;

}
