package com.eastwind.easyexcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/6 15:34
 */
@Setter
@Getter
@EqualsAndHashCode
public class ComplexHeadData {
    @ExcelProperty(value = {"主标题", "字符串子标题"})
    private String string;
    @ExcelProperty(value = {"主标题", "日期子标题"})
    private Date date;
    @ExcelProperty(value = {"主标题", "数字子标题"})
    private Double doubleData;
}
