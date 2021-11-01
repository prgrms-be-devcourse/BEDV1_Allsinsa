package com.progm.allsinsa.product.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.product.dto.ProductDto;
import com.progm.allsinsa.product.dto.ProductOptionNameRequest;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.dto.ProductOptionStockRequest;
import com.progm.allsinsa.product.service.ProductOptionService;

@WebMvcTest(ProductOptionController.class)
class ProductOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductOptionService productOptionService;

    private Long productId;
    private ProductDto productDto;
    private Long productOptionId;
    private ProductOptionResponse productOptionResponse;

    @BeforeEach
    void setUp() {
        productId = 1L;
        productDto = ProductDto.builder()
                .id(productId)
                .name("바지")
                .price(1000)
                .status("판매 중")
                .build();
        productOptionId = 1L;
        productOptionResponse = ProductOptionResponse.builder()
                .id(productOptionId)
                .productDto(productDto)
                .stock(20)
                .option1("흑청")
                .option2("30")
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

    @Test
    @DisplayName("제품 옵션 생성")
    void createProductOption() throws Exception {
        // given
        ProductOptionRequest productOptionRequest = ProductOptionRequest.builder()
                .productDto(productDto)
                .stock(20)
                .option1("흑청")
                .option2("30")
                .build();
        String requestBody = objectMapper.writeValueAsString(productOptionRequest);

        given(productOptionService.create(anyLong(), any()))
                .willReturn(productOptionResponse);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/products/{productId}/productOptions", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().string(
                        LOCATION, "/api/v1/products/" + productId + "/productOptions/" + productOptionResponse.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(productOptionId))
                .andExpect(jsonPath("productDto").exists())
                .andExpect(jsonPath("productDto.id").value(productId))
                .andExpect(jsonPath("productDto.name").value("바지"))
                .andExpect(jsonPath("stock").value(20))
                .andExpect(jsonPath("option1").value("흑청"))
                .andExpect(jsonPath("option2").value("30"))
                .andDo(print());
    }

    @Test
    @DisplayName("제품별 모든 옵션 조회")
    void findAllByProduct() throws Exception {
        // given
        long productOptionId2 = 2L;
        ProductOptionResponse productOptionResponse2 = ProductOptionResponse.builder()
                .id(productOptionId2)
                .productDto(productDto)
                .stock(10)
                .option1("진청")
                .option2("29")
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();

        given(productOptionService.findAllByProduct(productId))
                .willReturn(List.of(productOptionResponse, productOptionResponse2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/products/{productId}/productOptions", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(productOptionId))
                .andExpect(jsonPath("$[0].productDto").exists())
                .andExpect(jsonPath("$[0].productDto.id").value(productId))
                .andExpect(jsonPath("$[0].productDto.name").value("바지"))
                .andExpect(jsonPath("$[0].stock").value(20))
                .andExpect(jsonPath("$[0].option1").value("흑청"))
                .andExpect(jsonPath("$[0].option2").value("30"))
                .andExpect(jsonPath("$[1].id").value(productOptionId2))
                .andExpect(jsonPath("$[1].productDto").exists())
                .andExpect(jsonPath("$[1].productDto.id").value(productId))
                .andExpect(jsonPath("$[1].stock").value(10))
                .andExpect(jsonPath("$[1].option1").value("진청"))
                .andExpect(jsonPath("$[1].option2").value("29"))
                .andDo(print());
    }

    @Test
    @DisplayName("옵션명 변경")
    void updateOptionName() throws Exception {
        // given
        String newOptionName1 = "흑청", newOptionName2 = "28";
        ProductOptionNameRequest request = new ProductOptionNameRequest(newOptionName1, newOptionName2);
        String requestBody = objectMapper.writeValueAsString(request);

        given(productOptionService.updateOptionName(anyLong(), any()))
                .willReturn(ProductOptionResponse.builder()
                        .id(productOptionId)
                        .productDto(productDto)
                        .stock(20)
                        .option1(newOptionName1)
                        .option2(newOptionName2)
                        .build());

        // when
        ResultActions resultActions = mockMvc.perform(
                        patch("/api/v1/products/{productId}/productOptions/{productOptionId}/optionName", productId,
                                productOptionId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(productOptionId))
                .andExpect(jsonPath("productDto").exists())
                .andExpect(jsonPath("option1").value(newOptionName1))
                .andExpect(jsonPath("option2").value(newOptionName2))
                .andDo(print());
    }

    @Test
    @DisplayName("재고 추가")
    void addStock() throws Exception {
        // given
        int additionalStock = 5;
        ProductOptionStockRequest request = new ProductOptionStockRequest(additionalStock);
        String requestBody = objectMapper.writeValueAsString(request);

        given(productOptionService.addStock(anyLong(), anyInt()))
                .willReturn(productOptionResponse.getStock() + additionalStock);

        // when
        ResultActions resultActions = mockMvc.perform(
                        patch("/api/v1/products/{productId}/productOptions/{productOptionId}/stock", productId,
                                productOptionId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("25"))
                .andDo(print());
    }
}
