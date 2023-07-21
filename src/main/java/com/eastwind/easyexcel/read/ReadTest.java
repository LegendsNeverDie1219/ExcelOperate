package com.eastwind.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.eastwind.easyexcel.write.ExcelFileUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/7 10:00
 */
@Slf4j
public class ReadTest {
    private Gson gson = new Gson();
    @Test
    public void simpleRead() {
        String fileName = ExcelFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        EasyExcel.read(fileName,DemoData.class, new MyPageReadListener<DemoData>(demoDataList -> {
            System.out.println("累计缓存了100条数据.开始输出或者开始入库");
            for (DemoData demoData : demoDataList) {
                //log.info("读取到的数据为:" + JSON.toJSONString(demoData));
                log.info("读取到的数据为:" +gson.toJson(demoData));
            }
        })).sheet().doRead();

    }
}
