package com.progm.allsinsa.product;

import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    @Order(1)
    @DisplayName("새 상품을 추가할 수 있다.")
    public void testSave() {
        // GIVEN
        ProductRequestDto requestDto = new ProductRequestDto("새 상품명", 20000, "카테고리1", "판매중", "/src/img1", "/src/thm2", 1L);
        // WHEN
        long saveId = productService.save(requestDto);
        // THEN
        Optional<Product> productFind = productRepository.findById(saveId);
        assertAll(
                () ->  assertThat(productFind.isPresent(),is(true)),
                ()->   assertThat(productFind.get().getName(),is(requestDto.getName()))
        );

    }

    @Test
    @Order(2)
    @DisplayName("상품 Id로 상품 정보를 찾을 수 있다.")
    public void testGetOne() throws NotFoundException {
        // GIVEN
        ProductRequestDto requestDto = new ProductRequestDto("새 상품명", 20000, "카테고리1", "판매중", "/src/img1", "/src/thm2", 1L);
        long productId = productService.save(requestDto);

        // WHEN
        ProductDto foundProductDto = productService.findOneById(productId);

        // THEN
        assertAll(
                () ->  assertThat(foundProductDto.getName(),is(requestDto.getName())),
                ()->   assertThat(foundProductDto.getPrice(),is(requestDto.getPrice()))
        );
    }


    @Test
    @Order(3)
    @DisplayName("상품 목록을 가져올 수 있다(페이징)")
    public void testFindAll() {
        // GIVEN
        PageRequest page = PageRequest.of(0, 10);

        // WHEN
        Page<ProductDto> productDtos = productService.findAll(page);

        // THEN
        assertThat(productDtos.getSize(),is(greaterThan(0)));

    }

    @Test
    @Order(4)
    @DisplayName("상품 목록을 카테고리별로 가져올 수 있다(페이징)")
    public void testFindAllByCategory() {
        // GIVEN
        String categoryName = "카테고리1";
        PageRequest page = PageRequest.of(0, 10);

        // WHEN 
        Page<ProductDto> allByCategory = productService.findAllByCategory(categoryName, page);
        ProductDto firstPageResult = allByCategory.get().findFirst().get();

        // THEN
        assertThat(firstPageResult.getCategory(),is(categoryName));

    }
    



}