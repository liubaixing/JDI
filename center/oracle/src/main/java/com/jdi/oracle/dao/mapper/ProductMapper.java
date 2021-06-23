package com.jdi.oracle.dao.mapper;

import com.jdi.oracle.dao.entity.BrandMapping;
import com.jdi.oracle.dao.entity.Product;
import com.jdi.oracle.dao.entity.ProductNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    Product selectProductByName(@Param("name")String name);

    List<Product> selectProductByProductOrig(@Param("productOrig")String productOrig);

    List<BrandMapping> selectBrandMapping();

    ProductNew selectProductNewByProductId(@Param("productId")Long productId);

    boolean insertProductNew(ProductNew productNew);

}
