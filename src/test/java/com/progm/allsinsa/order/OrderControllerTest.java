package com.progm.allsinsa.order;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
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
                .andDo(print())
                .andDo(document("order-create", preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("orderDto").type(JsonFieldType.OBJECT).description("createOrderDto"),
                                fieldWithPath("orderDto.memberId").type(JsonFieldType.NUMBER)
                                        .description("createOrderDto.memberId"),
                                fieldWithPath("orderDto.recipientName").type(JsonFieldType.STRING)
                                        .description("createOrderDto.recipientName"),
                                fieldWithPath("orderDto.phoneNumber").type(JsonFieldType.STRING)
                                        .description("createOrderDto.phoneNumber"),
                                fieldWithPath("orderDto.shippingAddress").type(JsonFieldType.STRING)
                                        .description("createOrderDto.shippingAddress"),
                                fieldWithPath("orderDto.memo").type(JsonFieldType.STRING)
                                        .description("createOrderDto.memo"),
                                fieldWithPath("orderDto.totalAmount").type(JsonFieldType.NUMBER)
                                        .description("createOrderDto.totalAmount"),
                                fieldWithPath("orderProductDtos[]").type(JsonFieldType.ARRAY)
                                        .description("createOrderProductDto[]"),
                                fieldWithPath("orderProductDtos[].productName").type(JsonFieldType.STRING)
                                        .description("createOrderProductDto[].productName"),
                                fieldWithPath("orderProductDtos[].price").type(JsonFieldType.NUMBER)
                                        .description("createOrderProductDto[].price"),
                                fieldWithPath("orderProductDtos[].quantity").type(JsonFieldType.NUMBER)
                                        .description("createOrderProductDto[].quantity"),
                                fieldWithPath("orderProductDtos[].productOption").type(JsonFieldType.STRING)
                                        .description("createOrderProductDto[].productOption"),
                                fieldWithPath("orderProductDtos[].productOptionId").type(JsonFieldType.NUMBER)
                                        .description("createOrderProductDto[].productOptionId"),
                                fieldWithPath("orderProductDtos[].thumbnailImagePath").type(JsonFieldType.STRING)
                                        .description("createOrderProductDto[].thumbnailImagePath"),
                                fieldWithPath("orderProductDtos[].productId").type(JsonFieldType.NUMBER)
                                        .description("createOrderProductDto[].productId")
                        ),
                        responseFields(
                                fieldWithPath("orderNumber").type(JsonFieldType.STRING).description("주문번호")
                        )));
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-read-all", preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("pageOffset"),
                                parameterWithName("size").description("pageSize")
                        ),
                        responseFields(
                                beneathPath("content"),
                                fieldWithPath("recipientName").type(JsonFieldType.STRING).description("recipientName"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("phoneNumber"),
                                fieldWithPath("shippingAddress").type(JsonFieldType.STRING)
                                        .description("shippingAddress"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("orderNumber").type(JsonFieldType.STRING).description("orderNumber"),
                                fieldWithPath("totalAmount").type(JsonFieldType.NUMBER).description("totalAmount"),
                                fieldWithPath("savedAmount").type(JsonFieldType.NUMBER).description("savedAmount"),
                                fieldWithPath("paymentAmount").type(JsonFieldType.NUMBER).description("paymentAmount"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("orderProductDtos[]").type(JsonFieldType.ARRAY)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productName").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productOption").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].thumbnailImagePath").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].orderStatus").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].price").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].quantity").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productId").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos")
                        )));
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
                .andDo(print())
                .andDo(document("order-read-member", preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("memberId").description("memberId"),
                                parameterWithName("page").description("pageOffset"),
                                parameterWithName("size").description("pageSize")
                        ),
                        responseFields(
                                beneathPath("content"),
                                fieldWithPath("recipientName").type(JsonFieldType.STRING).description("recipientName"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("phoneNumber"),
                                fieldWithPath("shippingAddress").type(JsonFieldType.STRING)
                                        .description("shippingAddress"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("orderNumber").type(JsonFieldType.STRING).description("orderNumber"),
                                fieldWithPath("totalAmount").type(JsonFieldType.NUMBER).description("totalAmount"),
                                fieldWithPath("savedAmount").type(JsonFieldType.NUMBER).description("savedAmount"),
                                fieldWithPath("paymentAmount").type(JsonFieldType.NUMBER).description("paymentAmount"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("orderProductDtos[]").type(JsonFieldType.ARRAY)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productName").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productOption").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].thumbnailImagePath").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].orderStatus").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].price").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].quantity").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productId").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos")
                        )));
    }

    @Test
    void findOneTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/orders/{number}", orderNumber1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("order-read-number", preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("number").description("주문 번호")),
                        responseFields(
                                fieldWithPath("recipientName").type(JsonFieldType.STRING).description("recipientName"),
                                fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("phoneNumber"),
                                fieldWithPath("shippingAddress").type(JsonFieldType.STRING)
                                        .description("shippingAddress"),
                                fieldWithPath("memo").type(JsonFieldType.STRING).description("memo"),
                                fieldWithPath("orderNumber").type(JsonFieldType.STRING).description("orderNumber"),
                                fieldWithPath("totalAmount").type(JsonFieldType.NUMBER).description("totalAmount"),
                                fieldWithPath("savedAmount").type(JsonFieldType.NUMBER).description("savedAmount"),
                                fieldWithPath("paymentAmount").type(JsonFieldType.NUMBER).description("paymentAmount"),
                                fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("orderStatus"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("orderProductDtos[]").type(JsonFieldType.ARRAY)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productName").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productOption").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].thumbnailImagePath").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].orderStatus").type(JsonFieldType.STRING)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].price").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].quantity").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos"),
                                fieldWithPath("orderProductDtos[].productId").type(JsonFieldType.NUMBER)
                                        .description("orderProductDtos")
                        )));
    }
}
