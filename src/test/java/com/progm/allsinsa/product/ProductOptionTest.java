package com.progm.allsinsa.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;
import com.progm.allsinsa.product.dto.ProductOptionNameRequest;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.dto.ProductOptionStockDto;
import com.progm.allsinsa.product.repository.ProductOptionRepository;
import com.progm.allsinsa.product.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductOptionTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductRepository productRepository;

    private Long productId;
    private Product savedProduct;
    private String url;

    @BeforeEach
    void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        productOptionRepository.deleteAll();
        productRepository.deleteAll();

        Product product = Product.builder()
                .name("청바지")
                .price(1000)
                .category("하의")
                .build();
        savedProduct = productRepository.saveAndFlush(product);
        productId = savedProduct.getId();
        url = "/api/v1/products/" + productId + "/productOptions";
    }

    @Test
    @DisplayName("제품 옵션 생성")
    void create() {
        // given
        ProductOptionRequest request = new ProductOptionRequest(10, "블랙", null);

        // when
        ResponseEntity<ProductOptionResponse> response = restTemplate.postForEntity(url, request,
                ProductOptionResponse.class);

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(response.getBody()).isNotNull()
        );

        Optional<ProductOption> productOption = productOptionRepository.findById(response.getBody().getId());
        assertAll(
                () -> assertThat(productOption).isNotNull(),
                () -> assertThat(productOption.get().getProduct().getId()).isEqualTo(productId),
                () -> assertThat(productOption.get().getId()).isNotNull(),
                () -> assertThat(productOption.get().getStock()).isEqualTo(10),
                () -> assertThat(productOption.get().getOption1()).isEqualTo("블랙")
        );
    }

    @Test
    @DisplayName("전체 조회")
    void findAllByProduct() {
        // given
        saveProductOption(10, "블랙");
        saveProductOption(20, "네이비");

        Product product2 = new Product("면바지");
        productRepository.saveAndFlush(product2);
        ProductOption productOption3 = ProductOption.builder()
                .product(product2)
                .stock(30)
                .option1("아이보리")
                .build();

        productOptionRepository.save(productOption3);

        // when
        ResponseEntity<ProductOptionResponse[]> responseEntity = restTemplate.getForEntity(url,
                ProductOptionResponse[].class); // getAll: array -> list 변환 필요
        List<ProductOptionResponse> responses = Arrays.asList(responseEntity.getBody());

        // then
        assertAll(
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responseEntity.getBody().length).isEqualTo(2),
                () -> assertThat(responses).extracting("stock", "option1")
                        .containsOnly(tuple(10, "블랙"), tuple(20, "네이비"))
        );
    }

    @Test
    @DisplayName("옵션명 변경")
    void updateOptionName() {
        // given
        ProductOption saved = saveProductOption(10, "블랙");

        ProductOptionNameRequest request = new ProductOptionNameRequest("네이비", "L");
        HttpEntity<ProductOptionNameRequest> requestEntity = new HttpEntity<>(request);

        // when
        ResponseEntity<ProductOptionResponse> response = restTemplate.exchange(
                url + "/" + saved.getId() + "/optionName",
                HttpMethod.PATCH,
                requestEntity,
                ProductOptionResponse.class);

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getBody()).hasFieldOrPropertyWithValue("option1", "네이비"),
                () -> assertThat(response.getBody()).hasFieldOrPropertyWithValue("option2", "L")
        );
    }

    @Test
    @DisplayName("재고 추가")
    void addStock() {
        // given
        int stock = 10;
        ProductOption saved = saveProductOption(stock, "블랙");

        int additionalStock = 15;
        ProductOptionStockDto request = new ProductOptionStockDto(additionalStock);
        HttpEntity<ProductOptionStockDto> requestEntity = new HttpEntity<>(request);

        // when
        ResponseEntity<ProductOptionStockDto> response = restTemplate.exchange(
                url + "/" + saved.getId() + "/stock",
                HttpMethod.PATCH,
                requestEntity,
                ProductOptionStockDto.class);

        // then
        assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(response.getBody()).isNotNull(),
                () -> assertThat(response.getBody().getStock()).isEqualTo(stock + additionalStock)
        );
    }

    @Test
    @DisplayName("제품 옵션 삭제")
    void delete() {
        // given
        ProductOption saved = saveProductOption(10, "블랙");
        Long productOptionId = saved.getId();

        // when
        restTemplate.delete(url + "/" + productOptionId);
        Optional<ProductOption> productOption = productOptionRepository.findById(productOptionId);

        // then
        assertThat(productOption.isEmpty()).isTrue();
    }

    private ProductOption saveProductOption(int stock, String option1) {
        ProductOption productOption = ProductOption.builder()
                .product(savedProduct)
                .stock(stock)
                .option1(option1)
                .build();
        return productOptionRepository.saveAndFlush(productOption);
    }
}
