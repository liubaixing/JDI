package com.jdi.example.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.jdi.common.dto.CommonExcelEntity;
import com.jdi.common.listener.CommonExcelListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("excel")
public class ExcelController {

    @PostMapping("import")
    public String excelImport(@RequestParam("file")MultipartFile file) throws IOException {

        CommonExcelListener<CommonExcelEntity> listener = new CommonExcelListener<CommonExcelEntity>();
        EasyExcel.read(file.getInputStream(),CommonExcelEntity.class,listener).sheet().doRead();
        List<CommonExcelEntity> datas = listener.getDatas();
        List<String> row = datas.stream().map(CommonExcelEntity::getRow).collect(Collectors.toList());
        System.out.println(row);
        String json = JSONUtil.toJsonStr(datas);
        return json;
    }




}
