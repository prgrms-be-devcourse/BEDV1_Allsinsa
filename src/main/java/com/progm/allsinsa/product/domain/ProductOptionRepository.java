package com.progm.allsinsa.product.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findAllByProduct(Product product);

    List<ProductOption> findAllByProductAndOption1(Product product, String option1);

}
