package com.jdi.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CommonExcelEntity {

    @ExcelProperty(index = 0)
    private String row;

    @ExcelProperty(index = 1)
    private String row1;

    @ExcelProperty(index = 2)
    private String row2;

    @ExcelProperty(index = 3)
    private String row3;

    @ExcelProperty(index = 4)
    private String row4;

    @ExcelProperty(index = 5)
    private String row5;



}
