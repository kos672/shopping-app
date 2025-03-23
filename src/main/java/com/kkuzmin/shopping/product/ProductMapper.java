package com.kkuzmin.shopping.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "currency", target = "currency")
    Product mapToProduct(ProductDTO productDTO);

    List<ProductFullDTO> mapToProductFullDTOList(List<Product> products);

    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product", target = "product")
    ProductFullDTO mapToProductFullDTO(Product product);
}
