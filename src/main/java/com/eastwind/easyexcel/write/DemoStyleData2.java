package com.eastwind.easyexcel.write;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/7 9:04
 */
@Setter
@Getter
@EqualsAndHashCode
// IndexedColors.GREEN.getIndex();
@HeadStyle(fillPatternType = FillPatternTypeEnum.DEFAULT,fillForegroundColor =10)
@HeadFontStyle(fontHeightInPoints = 30)
@ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 17)
@ContentFontStyle(fontHeightInPoints = 20)
public class DemoStyleData2 {
    @HeadStyle(fillPatternType = FillPatternTypeEnum.DEFAULT,fillForegroundColor = 14)
    @HeadFontStyle(fontHeightInPoints = 30)
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
    @ContentFontStyle(fontHeightInPoints = 30)
    @ColumnWidth(22)
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    @ColumnWidth(40)
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;
}

