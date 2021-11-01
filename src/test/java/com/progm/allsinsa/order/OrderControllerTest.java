package com.progm.allsinsa.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.order.dto.CreateOrderDto;
import com.progm.allsinsa.order.dto.CreateOrderProductDto;
import com.progm.allsinsa.order.dto.CreateOrderRequestDto;
import com.progm.allsinsa.order.service.OrderService;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private String orderNumber1;
    private String orderNumber2;
    private Long memberId1;
    private Long memberId2;

    @BeforeEach
    void setup() throws Exception {
        memberId1 = 1L;
        memberId2 = 2L;
        CreateOrderDto createOrderDto1 = new CreateOrderDto(
                memberId1,
                "김현준1",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);
        CreateOrderProductDto createOrderProductDto1 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto2 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                5L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );
        CreateOrderDto createOrderDto2 = new CreateOrderDto(
                memberId2,
                "김현준2",
                "01026846867",
                "경기도 수원시 영통구 영통동",
                "부재시 경비실에 맡겨주세요",
                16000);

        CreateOrderProductDto createOrderProductDto3 = new CreateOrderProductDto(
                "바지",
                3000,
                2,
                "사이즈 : S",
                1L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                1L
        );
        CreateOrderProductDto createOrderProductDto4 = new CreateOrderProductDto(
                "신발",
                10000,
                1,
                "사이즈 : M",
                5L,
                "https://www.google.com/url?sa=i&url=http%3A%2F%2Fwww.sportsseoul.com%2Fnews%2Fread%2F988300&psig=AOvVaw33h5sXl1K-OPcKgT7BmBGK&ust=1635391936561000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPj-tfLT6fMCFQAAAAAdAAAAABAD",
                2L
        );

        CreateOrderRequestDto dto1 = new CreateOrderRequestDto(createOrderDto1,
                List.of(createOrderProductDto1, createOrderProductDto2));
        CreateOrderRequestDto dto2 = new CreateOrderRequestDto(createOrderDto2,
                List.of(createOrderProductDto3, createOrderProductDto4));

        orderNumber1 = orderService.createOrder(dto1);
        orderNumber2 = orderService.createOrder(dto2);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto1)))
                .andExpect(status().isCreated())
                .andDo(print());

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto2)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findMemberOrderTest() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("memberId", String.valueOf(memberId1))
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get("/api/v1/orders")
                        .param("memberId", String.valueOf(memberId2))
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
