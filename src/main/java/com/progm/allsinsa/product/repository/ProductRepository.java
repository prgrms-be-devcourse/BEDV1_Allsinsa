package com.progm.allsinsa.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
