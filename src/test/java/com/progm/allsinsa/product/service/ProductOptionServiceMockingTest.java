package com.progm.allsinsa.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;
import com.progm.allsinsa.product.dto.ProductDto;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.repository.ProductOptionRepository;
import com.progm.allsinsa.product.repository.ProductRepository;
import javassist.NotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceMockingTest {

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductOptionService productOptionService;

    private Product product;
    private Long productId;

    private ProductOption productOption;
    private int stock;
    private String option1;
    private String option2;

    @BeforeEach
    void setUp() {
        product = new Product("바지");
        productId = 1L;

        stock = 10;
        option1 = "블랙";
        option2 = "M";
        productOption = ProductOption.builder()
                .product(product)
                .stock(stock)
                .option1(option1)
                .option2(option2)
                .build();
        productOption.setCreatedAt(LocalDateTime.now());
        productOption.setUpdatedAt(LocalDateTime.now());

        Mockito.lenient().when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
    }

    @DisplayName("제품 옵션 생성")
    @Test
    void createProductOption() throws NotFoundException {
        // given
        ProductOptionRequest request = ProductOptionRequest.builder()
                .productDto(ProductDto.from(product))
                .stock(stock)
                .option1(option1)
                .build();
        when(productOptionRepository.save(any()))
                .thenReturn(productOption);

        // when
        ProductOptionResponse response = productOptionService.create(productId, request);

        // then
        assertAll(
                () -> verify(productOptionRepository, times(1)).save(any()),
                () -> assertThat(response.getProductDto().getName()).isEqualTo(product.getName()),
                () -> assertThat(response.getStock()).isEqualTo(stock),
                () -> assertThat(response.getOption1()).isEqualTo(option1),
                () -> assertThat(response.getOption2()).isEqualTo(option2)
        );
    }

    @Test
    @DisplayName("제품 옵션 구매시 재고 부족 예외")
    void purchaseProductOption() {
        // given
        int purchasedNum = 15;
        when(productOptionRepository.findById(any()))
                .thenReturn(Optional.of(productOption));

        // when, then
        assertThatThrownBy(() -> productOptionService.purchase(anyLong(), purchasedNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("재고가 부족합니다. product option ")
                .hasMessageContaining("의 재고: " + stock);
    }

    @DisplayName("제품 옵션 조회")
    @Nested
    class readTest {

        private ProductOption productOption2;
        private int stock2;
        private String opt1;
        private String opt2;

        @BeforeEach
        void setUp() {
            stock2 = 20;
            opt1 = "네이비";
            opt2 = "L";
            productOption2 = ProductOption.builder()
                    .product(product)
                    .stock(stock2)
                    .option1(opt1)
                    .option2(opt2)
                    .build();
            productOption2.setCreatedAt(LocalDateTime.now());
            productOption2.setUpdatedAt(LocalDateTime.now());
        }

        @DisplayName("존재하지 않는 아이디로 옵션 조회")
        @Test
        void failFindById() {
            // given
            Long InvalidProductionOptionId = -1L;
            when(productOptionRepository.findById(InvalidProductionOptionId))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> productOptionService.findById(InvalidProductionOptionId))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("product option이 존재하지 않습니다, product option id: " + InvalidProductionOptionId);

        }

        @DisplayName("제품별 옵션 조회")
        @Test
        void findAllByProduct() throws NotFoundException {
            // given
            when(productOptionRepository.findAllByProduct(product))
                    .thenReturn(Arrays.asList(productOption, productOption2));

            // when
            List<ProductOptionResponse> responses = productOptionService.findAllByProduct(productId);

            // then
            assertAll(
                    () -> verify(productOptionRepository, times(1)).findAllByProduct(any()),
                    () -> assertThat(responses)
                            .extracting("stock", "option1", "option2")
                            .containsOnly(tuple(stock2, opt1, opt2),
                                    tuple(stock, option1, option2))
            );
        }
    }
}
