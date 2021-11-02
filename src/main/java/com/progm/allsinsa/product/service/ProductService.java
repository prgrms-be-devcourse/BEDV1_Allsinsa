package com.progm.allsinsa.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.dto.ProductConverter;
import com.progm.allsinsa.product.dto.ProductDto;
import com.progm.allsinsa.product.dto.ProductRequestDto;
import com.progm.allsinsa.product.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public long save(ProductRequestDto productRequestDto) {
        Product savedProduct = productRepository.save(productConverter.convertRequestToProduct(productRequestDto));
        return savedProduct.getId();
    }

    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productConverter::convertToProductDto);

    }

    public Page<ProductDto> findAllByCategory(String categoryName, Pageable pageable) {
        return productRepository.findProductsByCategory(categoryName, pageable);
    }

    public ProductDto findOneById(long productId) throws NotFoundException {
        return productRepository.findById(productId)
                .map(productConverter::convertToProductDto)
                .orElseThrow(() -> new NotFoundException("invalid productId"));

    }

    public void deleteById(long productId) throws NotFoundException {
        productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("invalid productId"));
        productRepository.deleteById(productId);
    }

}
