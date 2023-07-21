package com.eastwind.easyexcel.write;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

public class ExcelFileUtil {
    @Test
    public void test() {
        // E:\IdeaProjects\javaproject\ExcelOperate\target\classes
        String path = getPath();
        File file = new File(path);
        File parentFile = file.getParentFile();
        // E:\IdeaProjects\javaproject\ExcelOperate\target
        System.out.println(parentFile);
    }


    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        // 如果以”/”开头，就在classpath根目录下找
        // classpath:编译后的class文件、xml、properties等配置文件所在的目录
        //  /E:/IdeaProjects/javaproject/ExcelOperate/target/classes/
        return ExcelFileUtil.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                // todo mkdirs会创建多级目录.
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }
}
