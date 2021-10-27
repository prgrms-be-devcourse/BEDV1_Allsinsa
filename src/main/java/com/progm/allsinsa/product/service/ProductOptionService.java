package com.progm.allsinsa.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.exception.NotFoundException;
import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;
import com.progm.allsinsa.product.domain.ProductOptionRepository;
import com.progm.allsinsa.product.domain.ProductRepository;
import com.progm.allsinsa.product.dto.ProductOptionNameRequest;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;

@Transactional
@Service
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    public ProductOptionService(ProductOptionRepository productOptionRepository,
            ProductRepository productRepository) {
        this.productOptionRepository = productOptionRepository;
        this.productRepository = productRepository;
    }

    public ProductOptionResponse create(Long productId, ProductOptionRequest request) {
        Product product = getProduct(productId);
        ProductOption productOption = request.to(product);
        ProductOption saved = productOptionRepository.save(productOption);

        return ProductOptionResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ProductOptionResponse findById(Long productOptionId) {
        ProductOption productOption = getProductOption(productOptionId);
        return ProductOptionResponse.from(productOption);
    }

    public ProductOptionResponse updateOption1(Long productOptionId, ProductOptionNameRequest request) {
        ProductOption productOption = getProductOption(productOptionId);
        productOption.updateOption1(request.getOption());
        return ProductOptionResponse.from(productOption);
    }

    public ProductOptionResponse updateOption2(Long productOptionId, ProductOptionNameRequest request) {
        ProductOption productOption = getProductOption(productOptionId);
        productOption.updateOption2(request.getOption());
        return ProductOptionResponse.from(productOption);
    }

    public void delete(Long productOptionId) {
        productOptionRepository.deleteById(productOptionId);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product가 존재하지 않습니다, product id: " + productId));
    }

    private ProductOption getProductOption(Long productOptionId) {
        return productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new NotFoundException(
                        "product option이 존재하지 않습니다, product option id: " + productOptionId));
    }
}
