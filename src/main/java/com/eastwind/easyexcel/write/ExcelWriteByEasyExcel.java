package com.eastwind.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.*;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/6 11:31
 */
public class ExcelWriteByEasyExcel {
    private List<DemoData> generateData() {
        List<DemoData> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setString("字符串" + i);
            demoData.setDate(new Date());
            demoData.setDoubleData(0.53);
            list.add(demoData);
        }
        return list;
    }

    private List<ComplexHeadData> generateComplexData() {
        List<ComplexHeadData> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            ComplexHeadData complexHeadData = new ComplexHeadData();
            complexHeadData.setString("字符串" + i);
            complexHeadData.setDate(new Date());
            complexHeadData.setDoubleData(0.53);
            list.add(complexHeadData);
        }
        return list;
    }

    @Test
    public void simpleWrite() {
        // 写法一:
        String fileName = ExcelFileUtil.getPath() + "simpleWrite_1" + "_" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, DemoData.class).excelType(ExcelTypeEnum.XLSX)
                .sheet("模板").doWrite(generateData());

        // 写法二:
        System.out.println("=============================================================");
        fileName = ExcelFileUtil.getPath() + "simpleWrite_2" + "_" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, DemoData.class).excelType(ExcelTypeEnum.XLSX)
                .sheet("模板").doWrite(this::generateData);


        // 写法三 todo
        System.out.println("=============================================================");
        fileName = ExcelFileUtil.getPath() + "simpleWrite_3" + "_" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter =
                     EasyExcel.write(fileName, DemoData.class).excelType(ExcelTypeEnum.XLSX).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().sheetName("页签一").build();
            excelWriter.write(generateData(), writeSheet);
        }
    }

    @Test
    public void excludeOrIncludeWrite() {
        String fileName = ExcelFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        Set<String> excludeColumnFieldNames = new HashSet<>();
        excludeColumnFieldNames.add("date");

        EasyExcel.write(fileName, DemoData.class).excludeColumnFieldNames(excludeColumnFieldNames).sheet("sheetName1")
                .doWrite(generateData());

        fileName = ExcelFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        Set<String> includeColumnFieldNames = new HashSet<>();
        includeColumnFieldNames.add("date");
        EasyExcel.write(fileName,DemoData.class).includeColumnFieldNames(includeColumnFieldNames).sheet("sheetName1").
                doWrite(generateData());
    }
    
    @Test
    public void indexWrite() {
        String fileName = ExcelFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName,IndexData.class).excelType(ExcelTypeEnum.XLSX).sheet("模板1219").doWrite(generateData());
    }
    
    @Test
    public void complexHeadWrite() {
        String filePath = ExcelFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(filePath,ComplexHeadData.class).excelType(ExcelTypeEnum.XLSX).sheet("模板1218").doWrite(generateData());
    }

    @Test
    public void repeatdWrite() {
        // 多次写入到一个页签中.
        String fileName = ExcelFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build()) {
            // todo 待写出的页签 对象.
            WriteSheet writeSheet = EasyExcel.writerSheet("页签名").build();
            for(int i = 0; i< 5; i++) {
                List<DemoData> demoData = generateData();
                excelWriter.write(demoData,writeSheet);
            }
        }

        // 多次写出到 不同的页签中.
        fileName = ExcelFileUtil.getPath() + "repeatdWrite" + System.currentTimeMillis() + ".xlsx";
        List<Class<?>> headList = Lists.newArrayList(DemoData.class, IndexData.class,ComplexHeadData.class,
                DemoData.class,DemoData.class);
        List<DemoData> demoData = generateData();
        List<ComplexHeadData> complexHeadData =generateComplexData();
        List<List<?>> dataList = Lists.newArrayList(demoData,demoData,demoData,demoData,complexHeadData);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            for(int i = 0; i < 5; i++){
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheetName" + i).head(headList.get(i)).build();
                List<?> data = dataList.get(i);
                excelWriter.write(data, writeSheet);
            }
        }
    }
    
    
    @Test
    public void convertWrite() {
        String fileName = ExcelFileUtil.getPath() + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName,ConverterData.class).sheet("模板").doWrite(generateData());
    }

    /**
     * 超链接、备注、公式、指定单个单元格的样式、单个单元格多种样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link WriteCellDemoData}
     * <p>
     * 2. 直接写即可
     *
     * @since 3.0.0-beta1
     */
    @Test
    public void writeCellDataWrite() {
        String fileName = ExcelFileUtil.getPath() + "writeCellDataWrite" + System.currentTimeMillis() + ".xlsx";
        WriteCellDemoData writeCellDemoData = new WriteCellDemoData();

        // 设置超链接
        WriteCellData<String> hyperlinkCellData = new WriteCellData<>("官方网站");

        HyperlinkData hyperlinkData = new HyperlinkData();
        hyperlinkData.setAddress("https://github.com/alibaba/easyexcel");
        hyperlinkData.setHyperlinkType(HyperlinkData.HyperlinkType.URL);
        hyperlinkCellData.setHyperlinkData(hyperlinkData);

        writeCellDemoData.setHyperlink(hyperlinkCellData);

        // 设置备注
        WriteCellData<String> comment = new WriteCellData<>("备注的单元格信息");

        CommentData commentData = new CommentData();
        commentData.setAuthor("Jiaju Zhuang");
        commentData.setRichTextStringData(new RichTextStringData("这是一个备注"));
        // 备注的默认大小是按照单元格的大小 这里想调整到4个单元格那么大 所以向后 向下 各额外占用了一个单元格
        commentData.setRelativeLastColumnIndex(1);
        commentData.setRelativeLastRowIndex(1);

        comment.setCommentData(commentData);
        writeCellDemoData.setCommentData(comment);


        // 设置公式
        WriteCellData<String> formula = new WriteCellData<>();
        FormulaData formulaData = new FormulaData();
        // 将 123456789 中的第一个数字替换成 2
        // 这里只是例子 如果真的涉及到公式 能内存算好尽量内存算好 公式能不用尽量不用
        formulaData.setFormulaValue("REPLACE(123456789,1,1,2)");
        formula.setFormulaData(formulaData);
        writeCellDemoData.setFormulaData(formula);

        // 设置单个单元格的样式 当然样式 很多的话 也可以用注解等方式。
        WriteCellData<String> writeCellStyle = new WriteCellData<>("单元格样式");
        writeCellStyle.setType(CellDataTypeEnum.STRING);
        writeCellDemoData.setWriteCellStyle(writeCellStyle);
        WriteCellStyle writeCellStyleData = new WriteCellStyle();
        writeCellStyle.setWriteCellStyle(writeCellStyleData);
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.
        writeCellStyleData.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        writeCellStyleData.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        // 设置单个单元格多种样式
        WriteCellData<String> richTest = new WriteCellData<>();
        richTest.setType(CellDataTypeEnum.RICH_TEXT_STRING);
        writeCellDemoData.setRichText(richTest);
        RichTextStringData richTextStringData = new RichTextStringData();
        richTest.setRichTextStringDataValue(richTextStringData);
        richTextStringData.setTextString("红色绿色默认");
        // 前2个字红色
        WriteFont writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.RED.getIndex());
        richTextStringData.applyFont(0, 2, writeFont);
        // 接下来2个字绿色
        writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.GREEN.getIndex());
        richTextStringData.applyFont(2, 4, writeFont);

        List<WriteCellDemoData> data = new ArrayList<>();
        data.add(writeCellDemoData);
        EasyExcel.write(fileName, WriteCellDemoData.class).inMemory(true).sheet("模板").doWrite(data);
    }

    @Test
    public void widthAndHeightWrite() {
      String filePath =   ExcelFileUtil.getPath() + "widthAndHeightWrite" +
              System.currentTimeMillis()+ ".xlsx";
      EasyExcel.write(filePath,WidthAndHeightData.class).sheet("模板").doWrite(generateData());
    }

    @Test
    public void annotationStyleWrite() {
        String fileName = ExcelFileUtil.getPath() + "annotationStyleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName,DemoStyleData.class).sheet("模板style").doWrite(generateData());
    }

    @Test
    public void annotationStyleWrite2() {
        String fileName = ExcelFileUtil.getPath() + "annotationStyleWrite_hds" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName,DemoStyleData2.class).sheet("模板style").doWrite(generateData());
    }




}
