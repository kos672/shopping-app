package com.kkuzmin.shopping.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public UUID createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.mapToProduct(productDTO);
        return this.productRepository.save(product).getId();
    }

    @Override
    public List<ProductFullDTO> getAllProducts() {
        return ProductMapper.INSTANCE.mapToProductFullDTOList(this.productRepository.findAll());
    }

    @Transactional
    @Override
    public Optional<ProductFullDTO> updateProduct(ProductDTO product) {
        Product existingProduct = this.productRepository.findByName(product.name());
        if (existingProduct == null) {
            return Optional.empty();
        }
        existingProduct.update(product);
        return Optional.of(ProductMapper.INSTANCE.mapToProductFullDTO(productRepository.save(existingProduct)));
    }

    @Override
    public List<ProductFullDTO> findProductsByNames(List<String> productNames) {
        List<Product> productsByNames = productRepository.findByNameIn(productNames);
        if (productNames.size() != productsByNames.size()) {
            productsByNames.stream().map(Product::getName)
                    .forEach(productFoundName -> {
                        if (!productNames.contains(productFoundName)) {
                            LOG.warn(String.format("Product with the name [%s] could not be found.", productFoundName));
                        }
                    });
        }
        return ProductMapper.INSTANCE.mapToProductFullDTOList(productsByNames);
    }
}
