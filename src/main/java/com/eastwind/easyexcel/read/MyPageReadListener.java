package com.eastwind.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * TODO
 *
 * @author Administrator
 * @date 2022/11/7 10:04
 */
public class MyPageReadListener<T> implements ReadListener<T> {
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(100);
    private Consumer<List<T>> consumer;

    public MyPageReadListener(Consumer<List<T>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() >= 100) {
            consumer.accept(cachedDataList);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(100);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
               consumer.accept(cachedDataList);
        }
    }
}

