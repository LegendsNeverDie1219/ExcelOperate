package com.eastwind.easyexcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/6 21:13
 */
@Setter
@Getter
@EqualsAndHashCode
@ContentRowHeight(20)
@HeadRowHeight(40)
@ColumnWidth(25)
public class WidthAndHeightData {
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ColumnWidth(50)
    @ExcelProperty("数字标题")
    private Double doubleData;

}
