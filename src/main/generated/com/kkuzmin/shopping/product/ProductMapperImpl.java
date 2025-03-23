package com.kkuzmin.shopping.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-23T19:36:01+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.13 (Eclipse Adoptium)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product mapToProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( productDTO.name() );
        product.setPrice( productDTO.price() );
        product.setCurrency( productDTO.currency() );

        return product;
    }

    @Override
    public List<ProductFullDTO> mapToProductFullDTOList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductFullDTO> list = new ArrayList<ProductFullDTO>( products.size() );
        for ( Product product : products ) {
            list.add( mapToProductFullDTO( product ) );
        }

        return list;
    }

    @Override
    public ProductFullDTO mapToProductFullDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        UUID id = null;
        ProductDTO product1 = null;

        id = product.getId();
        product1 = productToProductDTO( product );

        ProductFullDTO productFullDTO = new ProductFullDTO( id, product1 );

        return productFullDTO;
    }

    protected ProductDTO productToProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        String name = null;
        BigDecimal price = null;
        String currency = null;

        name = product.getName();
        price = product.getPrice();
        currency = product.getCurrency();

        ProductDTO productDTO = new ProductDTO( name, price, currency );

        return productDTO;
    }
}
