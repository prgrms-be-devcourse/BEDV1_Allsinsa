package com.progm.allsinsa.product.repository;

import com.progm.allsinsa.product.dto.ProductDto;
import com.progm.allsinsa.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<ProductDto> findProductsByCategory(String category, Pageable pageable);
}
