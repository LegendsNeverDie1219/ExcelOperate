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
 * @date 2022/11/6 15:25
 */
@Setter
@Getter
@EqualsAndHashCode
public class IndexData {
    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;
    @ExcelProperty(value =  "日期标题" ,index = 1)
    private Date date;
    /**
     * 这里设置3,会导致第二列是空的.[即第3列]
     */
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;
}
