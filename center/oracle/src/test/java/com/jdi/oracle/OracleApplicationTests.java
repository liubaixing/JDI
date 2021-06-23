package com.jdi.oracle;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jdi.common.dto.CommonExcelEntity;
import com.jdi.oracle.dao.entity.BrandMapping;
import com.jdi.oracle.dao.entity.Product;
import com.jdi.oracle.dao.entity.ProductNew;
import com.jdi.oracle.dao.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class OracleApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    private static Map<String,Long> brandMap;

    @Test
    void contextLoads() throws Exception {

        initBrand();

        // 被读取的文件绝对路径
        String fileName = "D:\\Users\\liubaixing23200\\Desktop\\微盟各商城上新款号（截至21春夏）---20210618.xlsx";

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
            }
        }).sheet("江南布衣官方Outlet商城").doRead();

        List<CommonExcelEntity> jnbyHome = new ArrayList<>();

        List<ProductNew> productNewList = commonExcelEntities.stream().map(i -> {

            String brandName = i.getRow1();
            String productName = i.getRow2();

            Long weid = chooseBrand(brandName);
            Product product = productMapper.selectProductByName(productName);
            ProductNew productNew = new ProductNew();
            productNew.setWeid(weid);
            productNew.setProductName(productName);
            if (product != null){
                productNew.setProductId(product.getId());
            }else if ("JNBYHOME".equals(brandName)){
                jnbyHome.add(i);
            }else {
                System.out.println(productName+"为查询到");
            }
            return productNew;
        }).collect(Collectors.toList());

        List<ProductNew> productNews = new ArrayList<>();
        for (CommonExcelEntity entity : jnbyHome){
            String brandName = entity.getRow1();
            String productName = entity.getRow2();

            Long weid = chooseBrand(brandName);


            List<Product> products = productMapper.selectProductByProductOrig(productName);

            if (products != null && products.size() > 0){
                products.stream().forEach(i -> {
                    ProductNew productNew = new ProductNew();
                    productNew.setWeid(weid);
                    productNew.setProductId(i.getId());
                    productNew.setProductName(i.getName());
                    productNews.add(productNew);
                });
            }

        }
        check(productNews);
//        check(productNewList);

    }

    private void check(List<ProductNew> productNewList) throws Exception {

        for (ProductNew productNew : productNewList){

            if (productNew.getWeid() == null || productNew.getProductId() == null || productNew.getProductName() == null){
                System.out.println("有值为null,"+productNew.toString());
                continue;
            }

            ProductNew productNew1 = productMapper.selectProductNewByProductId(productNew.getProductId());
            if (productNew1 != null){
                System.out.println("记录已存在");
                continue;
            }

            productMapper.insertProductNew(productNew);
        }
    }

    private Long chooseBrand(String brandName){

        if ("JNBYHOME".equals(brandName)){
            return brandMap.get("jnbyh");
        }else if ("APN73".equals(brandName) || "A PERSONAL NOTE 73".equals(brandName)){
            return brandMap.get("apn");
        }else if ("蓬马".equals(brandName)){
            return brandMap.get("pomme");
        }else if ("jnby by JNBY".equals(brandName) || "童装".equals(brandName)){
            return brandMap.get("tjnby");
        }else if ("LESS".equals(brandName)){
            return brandMap.get("less");
        }else if ("速写".equals(brandName) || "CROQUIS".equals(brandName)){
            return brandMap.get("croquis");
        }else if ("JNBY".equals(brandName)){
            return brandMap.get("jnby");
        }else{
            return null;
        }


    }


    private void initBrand(){
        List<BrandMapping> brandMappings = productMapper.selectBrandMapping();
        brandMap = brandMappings.stream().collect(Collectors.toMap(BrandMapping::getHash,BrandMapping::getWeid));
    }

}
