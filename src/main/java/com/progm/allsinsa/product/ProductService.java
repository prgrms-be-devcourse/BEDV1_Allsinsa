package com.progm.allsinsa.product;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public long save(ProductRequestDto productRequestDto){
        Product savedProduct = productRepository.save(productConverter.convertRequestToProduct(productRequestDto));
        return savedProduct.getId();
    }

    public Page<ProductDto> findAll(Pageable pageable){
        return productRepository.findAll(pageable)
                .map(productConverter::convertToProductDto);

    }

    public Page<ProductDto> findAllByCategory(String categoryName, Pageable pageable){
        List<ProductDto> productDtos = productRepository.findProductsByCategory(categoryName, pageable).stream()
                .map(productConverter::convertToProductDto).toList();
        return new PageImpl<>(productDtos,pageable,productDtos.size());

    }

    public ProductDto findOneById(long productId) throws NotFoundException {
        return productRepository.findById(productId)
                .map(productConverter::convertToProductDto)
                .orElseThrow(()->new NotFoundException("invalid productId"));

    }

    public void deleteById(long productId) throws NotFoundException {
        productRepository.findById(productId)
                .orElseThrow(()->new NotFoundException("invalid productId"));
        productRepository.deleteById(productId);
    }



}
