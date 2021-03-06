package com.progm.allsinsa.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.dto.ProductDto;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<ProductDto> findProductsByCategory(String category, Pageable pageable);
}
