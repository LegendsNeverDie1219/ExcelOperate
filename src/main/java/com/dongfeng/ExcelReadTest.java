package com.dongfeng;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/5 18:23
 */
public class ExcelReadTest {
    public static final String RELATIVE_PATH;

    static {
        RELATIVE_PATH =
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
                        "resources" + File.separator;
    }

    @Test
    public void testRead03() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(RELATIVE_PATH + "观众统计表.xls");
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row row1 = sheet.getRow(0);
            Cell cell11 = row1.getCell(0);
            System.out.println(cell11.getStringCellValue());
            Cell cell12 = row1.getCell(1);
            System.out.println(cell12.getNumericCellValue());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testRead07() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(RELATIVE_PATH + "观众统计表07.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row row1 = sheet.getRow(0);
            Cell cell11 = row1.getCell(0);
            System.out.println(cell11.getStringCellValue());
            Cell cell12 = row1.getCell(1);
            System.out.println(cell12.getNumericCellValue());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCellType() {
        String filePath = RELATIVE_PATH + File.separator + "会员消费商品明细表.xlsx";
        InputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(workbook == null) {
            return;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row titleRow = sheet.getRow(0);
        if (titleRow == null) {
            return;
        }
        int cellCount = titleRow.getPhysicalNumberOfCells();
        // 标题行
        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
            Cell titleCell = titleRow.getCell(cellIndex);
            if (titleCell != null) {
                int cellType = titleCell.getCellType();
                String cellValue = titleCell.getStringCellValue();
                System.out.print(cellValue + " | ");
            }
        }
        System.out.println();

        // 内容行
        FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
            Row contentRow = sheet.getRow(rowIndex);
            if (contentRow != null) {
                for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                    System.out.print("[" + (rowIndex + 1) + "-" + (cellIndex + 1) + "]");
                    Cell contentCell = contentRow.getCell(cellIndex);
                    if (contentCell != null) {
                        int cellType = contentCell.getCellType();
                        String cellValue = "";
                        switch (cellType) {
                            case Cell.CELL_TYPE_STRING: {
                                System.out.print("[String]");
                                cellValue = contentCell.getStringCellValue();
                                break;
                            }
                            case Cell.CELL_TYPE_BOOLEAN: {
                                System.out.print("[Boolean]");
                                cellValue = String.valueOf(contentCell.getBooleanCellValue());
                                break;
                            }
                            case Cell.CELL_TYPE_BLANK: {
                                System.out.print("[BLANK]");
                                break;
                            }
                            case Cell.CELL_TYPE_NUMERIC: {
                                System.out.print("{Numeric}");
                                if (DateUtil.isCellDateFormatted(contentCell)) {
                                    System.out.print("[日期]");
                                    Date date = contentCell.getDateCellValue();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    cellValue = simpleDateFormat.format(date);
                                } else {
                                    System.out.print("数字,为防止数字过长时以科学计数法显示,需要转化为字符串");
                                    contentCell.setCellType(Cell.CELL_TYPE_STRING);
                                    cellValue = contentCell.toString();
                                   // cellValue = String.valueOf(contentCell.getNumericCellValue());
                                }
                                break;
                            }
                            case Cell.CELL_TYPE_FORMULA:{
                                System.out.print("[公式]" + contentCell.getCellFormula());
                                CellValue evaluate = formulaEvaluator.evaluate(contentCell);
                                cellValue = evaluate.formatAsString();
                                break;
                            }
                            case Cell.CELL_TYPE_ERROR: {
                                System.out.print("[数据类型错误]");
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }
    }
    
    @Test
    public void testFormula() throws IOException {
        InputStream inputStream = new FileInputStream(RELATIVE_PATH + "计算公式.xls");
        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(4);
        Cell cell = row.getCell(0);

        FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_FORMULA:{
                String cellFormula = cell.getCellFormula();
                System.out.println(cellFormula);
                CellValue evaluate = formulaEvaluator.evaluate(cell);
                String cellValue = evaluate.formatAsString();
                System.out.println(cellValue);
                break;
            }
        }
        inputStream.close();
    }
}
