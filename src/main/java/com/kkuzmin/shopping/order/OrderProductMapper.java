package com.kkuzmin.shopping.order;

import com.kkuzmin.shopping.product.ProductFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderProductMapper {

    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "productDto.product.name", target = "name")
    @Mapping(source = "productDto.product.price", target = "price")
    @Mapping(source = "productDto.product.currency", target = "currency")
    @Mapping(source = "order", target = "order")
    OrderProduct mapToOrderProduct(ProductFullDTO productDto, CustomerOrder order);

}
