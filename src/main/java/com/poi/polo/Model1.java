package com.poi.polo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Model1 {
    private String department;
    private String workshop;
    private String process;
    private String reservedPortion;
    private String realScores;
    private double percentage;
    private String date;
}
