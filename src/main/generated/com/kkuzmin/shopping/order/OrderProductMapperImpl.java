package com.kkuzmin.shopping.order;

import com.kkuzmin.shopping.product.ProductDTO;
import com.kkuzmin.shopping.product.ProductFullDTO;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-23T21:06:46+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class OrderProductMapperImpl implements OrderProductMapper {

    @Override
    public OrderProduct mapToOrderProduct(ProductFullDTO productDto, CustomerOrder order) {
        if ( productDto == null && order == null ) {
            return null;
        }

        String name = null;
        BigDecimal price = null;
        String currency = null;
        if ( productDto != null ) {
            name = productDtoProductName( productDto );
            price = productDtoProductPrice( productDto );
            currency = productDtoProductCurrency( productDto );
        }
        CustomerOrder order1 = null;
        order1 = order;

        OrderProduct orderProduct = new OrderProduct( name, price, currency, order1 );

        orderProduct.setVersion( order.getVersion() );

        return orderProduct;
    }

    private String productDtoProductName(ProductFullDTO productFullDTO) {
        ProductDTO product = productFullDTO.product();
        if ( product == null ) {
            return null;
        }
        return product.name();
    }

    private BigDecimal productDtoProductPrice(ProductFullDTO productFullDTO) {
        ProductDTO product = productFullDTO.product();
        if ( product == null ) {
            return null;
        }
        return product.price();
    }

    private String productDtoProductCurrency(ProductFullDTO productFullDTO) {
        ProductDTO product = productFullDTO.product();
        if ( product == null ) {
            return null;
        }
        return product.currency();
    }
}
