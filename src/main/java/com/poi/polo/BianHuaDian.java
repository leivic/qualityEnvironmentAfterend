package com.poi.polo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BianHuaDian {
    private int id;
    private String gongWeiMinChen;
    private String shenHeRiQi;
    private String shenHeQuYu;
    private String leiXin;
    private String bianHuaDianLeiRong;
    private String okOrNoOk;
}
