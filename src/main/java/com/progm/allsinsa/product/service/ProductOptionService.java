package com.progm.allsinsa.product.service;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> findAllByProduct(Long productId) {
        List<ProductOption> productOptions = productOptionRepository.findAllByProduct(getProduct(productId));
        return ProductOptionResponse.list(productOptions);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> findAllByProductAndOption1(Long productId, ProductOptionNameRequest request) {
        List<ProductOption> productOptions = productOptionRepository.findAllByProductAndOption1(getProduct(productId),
                request.getOption());
        return ProductOptionResponse.list(productOptions);
    }

    public ProductOptionResponse updateOptionName(Long productOptionId, int optionNumber,
            ProductOptionNameRequest request) {
        ProductOption productOption = getProductOption(productOptionId);
        if (optionNumber == 1) {
            productOption.updateOption1(request.getOption());
        } else if (optionNumber == 2) {
            productOption.updateOption2(request.getOption());
        }
        return ProductOptionResponse.from(productOption);
    }

    public int purchase(Long productOptionId, int purchasedNum) {
        ProductOption productOption = getProductOption(productOptionId);
        return productOption.purchaseProductOption(purchasedNum);
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
