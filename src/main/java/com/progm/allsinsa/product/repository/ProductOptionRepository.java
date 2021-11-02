package com.progm.allsinsa.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProduct(Product product);

    List<ProductOption> findAllByProductAndOption1(Product product, String option1);

}
