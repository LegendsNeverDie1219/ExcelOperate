package com.dongfeng;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/5 10:02
 */
public class ExcelWriteTest {
    public static final String RELATIVE_PATH;

    static {
        RELATIVE_PATH =
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
                        "resources" + File.separator;
    }

    @Test
    public void testPath() {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        System.out.println(RELATIVE_PATH);
    }

    @Test
    public void testReadJdbcProperties1() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        InputStream resourceAsStream = systemClassLoader.getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resourceAsStream != null) {
                    resourceAsStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String userName = properties.getProperty("username");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");


        System.out.println(userName);
        System.out.println(password);
        System.out.println(url);
        System.out.println(driverClass);
    }

    @Test
    public void testReadJdbcProperties2() {
        Properties properties = new Properties();
        BufferedReader bufferedReader = null;
        String regex = "\\s*#";
        Pattern pattern = Pattern.compile(regex);


        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(RELATIVE_PATH + "jdbc" +
                    ".properties"), StandardCharsets.UTF_8));

            String oneLine = bufferedReader.readLine();
            while (oneLine != null) {
                if (pattern.matcher(oneLine).find()) {
                    oneLine = bufferedReader.readLine();
                } else {
                    String[] split = oneLine.split("=");
                    properties.setProperty(split[0], split[1]);
                    oneLine = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader!= null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " : " + value);
        }
    }

    @Test
    public void testRegex() {
        String regex = "\\s*#";
        Pattern pattern = Pattern.compile(regex);
        List<String> strList = Lists.newArrayList("#1", "# 2", " #3","# 4", "   #5","    # 6","aaa");
        strList.forEach(item -> {
            Matcher matcher = pattern.matcher(item);
            boolean matches = matcher.find();
            // boolean matches = matcher.find();
            System.out.println(item + " matches: " + matches);
        });
    }

    @Test
    public void testWrite03() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("观众统计表");
        Row firstRow = sheet.createRow(0);
        Cell cell11 = firstRow.createCell(0);
        cell11.setCellValue("今日新增关注");

        Cell cell12 = firstRow.createCell(1);
        cell12.setCellValue(999);

        Row secondRow = sheet.createRow(1);

        Cell cell21 = secondRow.createCell(0);
        cell21.setCellValue("统计时间");

        Cell cell22 = secondRow.createCell(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDateStr = simpleDateFormat.format(new Date());
        cell22.setCellValue(formatDateStr);
        String excelOutPutPath = RELATIVE_PATH + "观众统计表.xls";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(excelOutPutPath);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件生成成功");
    }

    @Test
    public void testWrite07() {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("观众统计表");
        Row firstRow = sheet.createRow(0);
        Cell cell11 = firstRow.createCell(0);
        cell11.setCellValue("今日新增关注");

        Cell cell12 = firstRow.createCell(1);
        cell12.setCellValue(999);

        Row secondRow = sheet.createRow(1);

        Cell cell21 = secondRow.createCell(0);
        cell21.setCellValue("统计时间");

        Cell cell22 = secondRow.createCell(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDateStr = simpleDateFormat.format(new Date());
        cell22.setCellValue(formatDateStr);
        String excelOutPutPath = RELATIVE_PATH + "观众统计表07.xlsx";

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(excelOutPutPath);
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件生成成功");
    }

    @Test
    public void testWrite03BigData() {
        long begin = System.currentTimeMillis();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        List<String> titleColumns = Lists.newArrayList("姓名", "年龄", "住址");
        for (int i = 0; i < titleColumns.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titleColumns.get(i));
        }

        List<String> dataList = Lists.newArrayList("hds", "26", "曙光星城");
        for(int rowNum = 1; rowNum< 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for(int cellNum = 0; cellNum< 3; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(dataList.get(cellNum));
            }
        }

        System.out.println("done");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(RELATIVE_PATH + "bigData03.xls");
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("花费的时间为: "+ (end-begin)/1000);
    }

    @Test
    public void testWrite07BigData() {
        long begin = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        List<String> titleColumns = Lists.newArrayList("姓名", "年龄", "住址");
        for (int i = 0; i < titleColumns.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titleColumns.get(i));
        }

        List<String> dataList = Lists.newArrayList("hds", "26", "曙光星城");
        for(int rowNum = 1; rowNum< 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for(int cellNum = 0; cellNum< 3; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(dataList.get(cellNum));
            }
        }

        System.out.println("done");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(RELATIVE_PATH + "bigData07.xlsx");
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 花费的时间为: 4
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为: "+ (end-begin)/1000);
    }

    @Test
    public void testWrite07BigDataFast() {
        long begin = System.currentTimeMillis();
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row titleRow = sheet.createRow(0);
        List<String> titleColumns = Lists.newArrayList("姓名", "年龄", "住址");
        for (int i = 0; i < titleColumns.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titleColumns.get(i));
        }

        List<String> dataList = Lists.newArrayList("hds", "26", "曙光星城");
        for(int rowNum = 1; rowNum< 65536; rowNum++) {
            Row row = sheet.createRow(rowNum);
            for(int cellNum = 0; cellNum< 3; cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(dataList.get(cellNum));
            }
        }

        System.out.println("done");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(RELATIVE_PATH + "bigData07_fast.xlsx");
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                } // todo 清除临时文件.
                ((SXSSFWorkbook) workbook).dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("花费的时间为: "+ (end-begin)/1000);
    }
}
