package com.jdi.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jdi.common.dto.CommonExcelEntity;

import java.util.ArrayList;
import java.util.List;

public class ExeclTest {

    public static void main(String[] args) {

        // 被读取的文件绝对路径
        String fileName = "C:\\Users\\liubx\\Desktop\\微盟各商城上新款号（截至21春夏）---20210618.xlsx";

        // 接收解析出的目标对象（Student）
        List<CommonExcelEntity> commonExcelEntities = new ArrayList<>();

        // 这里需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭CommonExcelListener
        // excel中表的列要与对象的字段相对应
        EasyExcel.read(fileName, CommonExcelEntity.class, new AnalysisEventListener<CommonExcelEntity>() {

            // 每解析一条数据都会调用该方法
            @Override
            public void invoke(CommonExcelEntity commonExcelEntity, AnalysisContext analysisContext) {
                commonExcelEntities.add(commonExcelEntity);
            }

            // 解析完毕的回调方法
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("excel文件读取完毕！");
            }
        }).sheet("江南布衣官方Outlet商城").doRead();

        System.out.println("");

    }

}
