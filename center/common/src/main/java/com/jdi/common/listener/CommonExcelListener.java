package com.jdi.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommonExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> datas = new ArrayList<>();

    public List<T> getDatas(){
        return datas;
    }


    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        datas.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
