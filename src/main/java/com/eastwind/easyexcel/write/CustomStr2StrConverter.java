package com.eastwind.easyexcel.write;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/6 16:31
 */
public class CustomStr2StrConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    // 这里是读取的内存的时候会调用.
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return context.getReadCellData().getStringValue();
    }

    //  这里是写出到磁盘的时候会调用.
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) throws Exception {
        return new WriteCellData<>("自定义:" + context.getValue());
    }
}
