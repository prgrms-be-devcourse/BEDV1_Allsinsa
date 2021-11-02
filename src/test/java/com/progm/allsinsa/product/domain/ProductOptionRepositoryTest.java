package com.progm.allsinsa.product.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.progm.allsinsa.product.repository.ProductOptionRepository;

@DataJpaTest
class ProductOptionRepositoryTest {

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private List<String> option1s, option2s;
    private Product product;
    private List<ProductOption> productOptions;

    @BeforeEach
    void setUp() {
        option1s = new ArrayList<>() {{
            add("블랙");
            add("네이비");
        }};
        option2s = new ArrayList<>() {{
            add("M");
            add("L");
            add("XL");
        }};
        product = new Product("후드티");
        productOptions = new ArrayList<>() {
            {
                add(new ProductOption(product, 10, option1s.get(0), option2s.get(0)));
                add(new ProductOption(product, 20, option1s.get(0), option2s.get(1)));
                add(new ProductOption(product, 30, option1s.get(0), option2s.get(2)));
                add(new ProductOption(product, 40, option1s.get(1), option2s.get(1)));
                add(new ProductOption(product, 50, option1s.get(1), option2s.get(2)));
            }
        };

        entityManager.persistAndFlush(product);
        productOptions.forEach(productOption -> entityManager.persistAndFlush(productOption));
    }

    @DisplayName("제품의 모든 옵션 조회")
    @Test
    void findAllByProduct() {
        // when
        List<ProductOption> all = productOptionRepository.findAllByProduct(product);

        // then
        assertAll(
                () -> assertThat(all).hasSize(5),
                () -> assertThat(all).extracting("option1")
                        .containsOnly(option1s.get(0), option1s.get(1)),
                () -> assertThat(all).extracting("option2")
                        .containsOnly(option2s.get(0), option2s.get(1), option2s.get(2))
        );
    }

    @DisplayName("제품의 옵션1로 모든 옵션 조회")
    @Test
    void findAllByProductAndOption1() {
        // when
        List<ProductOption> all = productOptionRepository.findAllByProductAndOption1(product, option1s.get(1));

        // then
        assertAll(
                () -> assertThat(all).hasSize(2),
                () -> assertThat(all).extracting("option2")
                        .containsOnly(option2s.get(1), option2s.get(2))
        );
    }

}
