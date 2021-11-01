package com.progm.allsinsa.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;
import com.progm.allsinsa.product.domain.ProductOptionRepository;
import com.progm.allsinsa.product.domain.ProductRepository;
import com.progm.allsinsa.product.dto.ProductOptionNameRequest;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import javassist.NotFoundException;

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

    public ProductOptionResponse create(Long productId, ProductOptionRequest request) throws NotFoundException {
        Product product = getProduct(productId);
        ProductOption productOption = request.to(product);
        ProductOption saved = productOptionRepository.save(productOption);

        return ProductOptionResponse.from(saved);
    }

    // Cart에서 사용
    @Transactional(readOnly = true)
    public ProductOptionResponse findById(Long productOptionId) throws NotFoundException {
        ProductOption productOption = getProductOption(productOptionId);
        return ProductOptionResponse.from(productOption);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> findAllByProduct(Long productId) throws NotFoundException {
        List<ProductOption> productOptions = productOptionRepository.findAllByProduct(getProduct(productId));
        return ProductOptionResponse.list(productOptions);
    }

    @Transactional(readOnly = true)
    public List<ProductOptionResponse> findAllByProductAndOption1(Long productId,
            ProductOptionNameRequest request) throws
            NotFoundException {
        List<ProductOption> productOptions = productOptionRepository.findAllByProductAndOption1(getProduct(productId),
                request.getOption1());
        return ProductOptionResponse.list(productOptions);
    }

    public ProductOptionResponse updateOptionName(Long productOptionId, ProductOptionNameRequest request) throws
            NotFoundException {
        ProductOption productOption = getProductOption(productOptionId);
        productOption.updateOptionName(request.getOption1(), request.getOption2());
        return ProductOptionResponse.from(productOption);
    }

    // Order에서 사용
    public int purchase(Long productOptionId, int purchasedNum) throws NotFoundException {
        ProductOption productOption = getProductOption(productOptionId);
        return productOption.purchaseProductOption(purchasedNum);
    }

    public int addStock(Long productOptionId, int additionalStock) throws NotFoundException {
        ProductOption productOption = getProductOption(productOptionId);
        return productOption.addStock(additionalStock);
    }

    public void delete(Long productOptionId) {
        productOptionRepository.deleteById(productOptionId);
    }

    private Product getProduct(Long productId) throws NotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product가 존재하지 않습니다, product id: " + productId));
    }

    private ProductOption getProductOption(Long productOptionId) throws NotFoundException {
        return productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new NotFoundException(
                        "product option이 존재하지 않습니다, product option id: " + productOptionId));
    }
}
