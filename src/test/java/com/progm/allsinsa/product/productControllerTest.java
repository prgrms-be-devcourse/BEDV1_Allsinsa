package com.progm.allsinsa.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class productControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("새 상품 추가 api")
    @Order(1)
    public void testCreate() throws Exception {
        // GIVEN
        ProductRequestDto requestDto = new ProductRequestDto("새 상품", 20000, "카테고리2", "판매중", "/src/img", "src/thm", 1L);

        // WHEN - THEN
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    @DisplayName("상품 단건조회 api")
    @Order(2)
    public void testGetOne() throws Exception {
        // GIVEN
        ProductRequestDto requestDto = new ProductRequestDto("새 상품2", 20000, "카테고리2", "판매중", "/src/img", "src/thm", 1L);
        long savedId = productService.save(requestDto);

        // WHEN - THEN
        mockMvc.perform(get("/api/v1/product/{id}",savedId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 다건조회(페이징) api")
    @Order(3)
    public void testGetAll() throws Exception {
        // GIVEN
        ProductRequestDto requestDto = new ProductRequestDto("새 상품2", 20000, "카테고리2", "판매중", "/src/img", "src/thm", 1L);
        long savedId = productService.save(requestDto);

        // WHEN - THEN
        mockMvc.perform(get("/api/v1/product/")
                        .param("page",String.valueOf(0))
                        .param("size",String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("상품 카페고리별 조최 api")
    @Order(4)
    public void testGetAllByCategory() throws Exception {
        // GIVEN
        String category = "카테고리2";

        // WHEN - THEN
        mockMvc.perform(get("/api/v1/product/category")
                        .param("category",category)
                        .param("page",String.valueOf(0))
                        .param("size",String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}