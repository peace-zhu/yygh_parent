package com.atguigu.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<UserData> {
    @Override
    public void invoke(UserData userData, AnalysisContext analysisContext) {
        //从第二行开始读
        System.out.println(userData);
    }
    @Override
    public void invokeHeadMap(Map<Integer,String> headMap, AnalysisContext context){
        System.out.println("表头信息"+headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
