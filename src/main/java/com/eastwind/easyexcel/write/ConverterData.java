package com.eastwind.easyexcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/6 16:10
 */
@Setter
@Getter
@EqualsAndHashCode
public class ConverterData {
    // 自定义的 转化器. 不管数据库传过来什么, 给他加上自定义:
    @ExcelProperty(value = "字符串标题",converter = CustomStr2StrConverter.class)
    private String string;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = "日期标题")
    private Date date;

    @NumberFormat("#%")
    @ExcelProperty(value = "数字标题")
    private Double doubleData;

}
