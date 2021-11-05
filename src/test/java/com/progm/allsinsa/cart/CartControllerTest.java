package com.progm.allsinsa.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.cart.dto.CartDto;
import com.progm.allsinsa.cart.dto.CartProductDto;
import com.progm.allsinsa.cart.service.CartProductService;
import com.progm.allsinsa.cart.service.CartService;

import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.service.MemberService;
import com.progm.allsinsa.product.dto.ProductOptionRequest;
import com.progm.allsinsa.product.dto.ProductOptionResponse;
import com.progm.allsinsa.product.dto.ProductRequestDto;
import com.progm.allsinsa.product.service.ProductOptionService;
import com.progm.allsinsa.product.service.ProductService;
import javassist.NotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    MemberDto memberDto;
    CartProductDto cartProductDto;
    Long productId;

    @BeforeEach
    void setUp() throws NotFoundException {
        this.memberDto = memberService.createMember(
            MemberDto.builder()
                .name("memberA")
                .email("test123@gmail.com")
                .password("Test123!")
                .build()
        );

        this.productId = productService.save(ProductRequestDto.builder()
            .name("productA")
            .price(10000)
            .status("Hat")
            .thumbnailImgPath("URL")
            .build());

        ProductOptionResponse sizeL = productOptionService.create(
            productId,
            ProductOptionRequest.builder()
                .stock(20)
                .option1("size L")
                .option2("color Red")
                .build()
        );

        CartDto cartDto = cartService.findCartByMemberId(memberDto.getId());

        this.cartProductDto = cartProductService.saveCartProduct(
            cartDto.getId(),
            CartProductDto.builder()
                    .count(2)
                    .productOptionDto(productOptionService.findById(sizeL.getId()))
                .build()
        );
    }

    @Test
    @Transactional
    void cartReadTest() throws Exception {

        Long memberId = memberDto.getId();
        CartDto savedCartDto = cartService.findCartByMemberId(memberId);

        mockMvc.perform(get("/api/v1/carts/{cartId}", savedCartDto.getId())
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document
                (
                    "cart-read",
                    preprocessRequest(modifyUris().removePort(), prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                        fieldWithPath("memberDto.id").type(JsonFieldType.NUMBER).description("memberDto.id"),
                        fieldWithPath("memberDto.email").type(JsonFieldType.STRING).description("memberDto.email"),
                        fieldWithPath("memberDto.password").type(JsonFieldType.STRING).description("memberDto.password"),
                        fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                        fieldWithPath("cartProductDtos[]").type(JsonFieldType.ARRAY).description("cartProductDtos"),
                        fieldWithPath("cartProductDtos[].id").type(JsonFieldType.NUMBER).description("cartProductDtos.id"),
                        fieldWithPath("cartProductDtos[].count").type(JsonFieldType.NUMBER).description("cartProductDtos.count"),
                        fieldWithPath("cartProductDtos[].productOptionDto").type(JsonFieldType.OBJECT).description("cartProductDtos.productOptionDto"),
                        fieldWithPath("cartProductDtos[].productOptionDto.id").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.id"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto").type(JsonFieldType.OBJECT).description("cartProductDtos.productOptionDto.productDto"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.productDto.id"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.name").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.name"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.productDto.price"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.status").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.status"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("cartProductDtos[].productOptionDto.stock").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.stock"),
                        fieldWithPath("cartProductDtos[].productOptionDto.option1").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.option1"),
                        fieldWithPath("cartProductDtos[].productOptionDto.option2").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.option2"),
                        fieldWithPath("cartProductDtos[].productOptionDto.createdAt").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.createdAt"),
                        fieldWithPath("cartProductDtos[].productOptionDto.updatedAt").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.updatedAt")

                    )
                )
            );
    }

    @Test
    @Transactional
    void cartProductSaveTest() throws Exception {
        // given
        Long memberId = memberDto.getId();
        CartDto savedCartDto = cartService.findCartByMemberId(memberId);
        ProductOptionResponse sizeM = productOptionService.create(
            productId,
            ProductOptionRequest.builder()
                .stock(20)
                .option1("size M")
                .option2("color Red")
                .build()
        );

        this.cartProductDto = cartProductService.saveCartProduct(
            savedCartDto.getId(),
            CartProductDto.builder()
                .count(2)
                .productOptionDto(productOptionService.findById(sizeM.getId()))
                .build()
        );

        //then
        cartProductService.saveCartProduct(savedCartDto.getId(), savedCartDto.getCartProductDtos()
            .get(0));

        mockMvc.perform(post("/api/v1/cart-products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedCartDto))
            )
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document
                (
                    "cartProducts-save",
                    preprocessRequest(modifyUris().removePort(), prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                        fieldWithPath("memberDto.id").type(JsonFieldType.NUMBER).description("memberDto.id"),
                        fieldWithPath("memberDto.email").type(JsonFieldType.STRING).description("memberDto.email"),
                        fieldWithPath("memberDto.password").type(JsonFieldType.STRING).description("memberDto.password"),
                        fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                        fieldWithPath("cartProductDtos[]").type(JsonFieldType.ARRAY).description("cartProductDtos"),
                        fieldWithPath("cartProductDtos[].id").type(JsonFieldType.NUMBER).description("cartProductDtos.id").ignored(),
                        fieldWithPath("cartProductDtos[].count").type(JsonFieldType.NUMBER).description("cartProductDtos.count"),
                        fieldWithPath("cartProductDtos[].productOptionDto").type(JsonFieldType.OBJECT).description("cartProductDtos.productOptionDto"),
                        fieldWithPath("cartProductDtos[].productOptionDto.id").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.id"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto").type(JsonFieldType.OBJECT).description("cartProductDtos.productOptionDto.productDto"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.productDto.id"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.name").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.name"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.productDto.price"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.status").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.status"),
                        fieldWithPath("cartProductDtos[].productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("cartProductDtos[].productOptionDto.stock").type(JsonFieldType.NUMBER).description("cartProductDtos.productOptionDto.stock"),
                        fieldWithPath("cartProductDtos[].productOptionDto.option1").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.option1"),
                        fieldWithPath("cartProductDtos[].productOptionDto.option2").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.option2"),
                        fieldWithPath("cartProductDtos[].productOptionDto.createdAt").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.createdAt"),
                        fieldWithPath("cartProductDtos[].productOptionDto.updatedAt").type(JsonFieldType.STRING).description("cartProductDtos.productOptionDto.updatedAt")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("count"),
                        fieldWithPath("productOptionDto").type(JsonFieldType.OBJECT).description("productOptionDto"),
                        fieldWithPath("productOptionDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.id"),
                        fieldWithPath("productOptionDto.productDto").type(JsonFieldType.OBJECT).description("productOptionDto.productDto"),
                        fieldWithPath("productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.id"),
                        fieldWithPath("productOptionDto.productDto.name").type(JsonFieldType.STRING).description("productOptionDto.productDto.name"),
                        fieldWithPath("productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.price"),
                        fieldWithPath("productOptionDto.productDto.status").type(JsonFieldType.STRING).description("productOptionDto.productDto.status"),
                        fieldWithPath("productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("productOptionDto.stock").type(JsonFieldType.NUMBER).description("productOptionDto.stock"),
                        fieldWithPath("productOptionDto.option1").type(JsonFieldType.STRING).description("productOptionDto.option1"),
                        fieldWithPath("productOptionDto.option2").type(JsonFieldType.STRING).description("productOptionDto.option2"),
                        fieldWithPath("productOptionDto.createdAt").type(JsonFieldType.STRING).description("productOptionDto.createdAt"),
                        fieldWithPath("productOptionDto.updatedAt").type(JsonFieldType.STRING).description("productOptionDto.updatedAt")
                    )
                )
            );
    }

    @Test
    @Transactional
    void cartProductDeleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/cart-products/{cartProductId}", cartProductDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document
                (
                    "cartProducts-delete",
                    preprocessRequest(modifyUris().removePort(), prettyPrint()),
                    preprocessResponse(prettyPrint())
                )
            );
    }

    @Test
    @Transactional
    void cartProductFindTest() throws Exception {
        mockMvc.perform(get("/api/v1/cart-products/{cartProductId}", cartProductDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document
                (
                    "cartProducts-find",
                    preprocessRequest(modifyUris().removePort(), prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("count"),
                        fieldWithPath("productOptionDto").type(JsonFieldType.OBJECT).description("productOptionDto"),
                        fieldWithPath("productOptionDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.id"),
                        fieldWithPath("productOptionDto.productDto").type(JsonFieldType.OBJECT).description("productOptionDto.productDto"),
                        fieldWithPath("productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.id"),
                        fieldWithPath("productOptionDto.productDto.name").type(JsonFieldType.STRING).description("productOptionDto.productDto.name"),
                        fieldWithPath("productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.price"),
                        fieldWithPath("productOptionDto.productDto.status").type(JsonFieldType.STRING).description("productOptionDto.productDto.status"),
                        fieldWithPath("productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("productOptionDto.stock").type(JsonFieldType.NUMBER).description("productOptionDto.stock"),
                        fieldWithPath("productOptionDto.option1").type(JsonFieldType.STRING).description("productOptionDto.option1"),
                        fieldWithPath("productOptionDto.option2").type(JsonFieldType.STRING).description("productOptionDto.option2"),
                        fieldWithPath("productOptionDto.createdAt").type(JsonFieldType.STRING).description("productOptionDto.createdAt"),
                        fieldWithPath("productOptionDto.updatedAt").type(JsonFieldType.STRING).description("productOptionDto.updatedAt")
                    )
                )
            );
    }

    @Test
    @Transactional
    void cartProductFixTest() throws Exception {
        int fixedCount = 30;
        CartProductDto fixedCartProductDto = new CartProductDto(
            cartProductDto.getId(),
            fixedCount,
            cartProductDto.getProductOptionDto()
            );

        mockMvc.perform(put("/api/v1/cart-products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fixedCartProductDto))
            )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document
                (
                    "cartProducts-fix",
                    preprocessRequest(modifyUris().removePort(), prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("count"),
                        fieldWithPath("productOptionDto").type(JsonFieldType.OBJECT).description("productOptionDto"),
                        fieldWithPath("productOptionDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.id"),
                        fieldWithPath("productOptionDto.productDto").type(JsonFieldType.OBJECT).description("productOptionDto.productDto"),
                        fieldWithPath("productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.id"),
                        fieldWithPath("productOptionDto.productDto.name").type(JsonFieldType.STRING).description("productOptionDto.productDto.name"),
                        fieldWithPath("productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.price"),
                        fieldWithPath("productOptionDto.productDto.status").type(JsonFieldType.STRING).description("productOptionDto.productDto.status"),
                        fieldWithPath("productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("productOptionDto.stock").type(JsonFieldType.NUMBER).description("productOptionDto.stock"),
                        fieldWithPath("productOptionDto.option1").type(JsonFieldType.STRING).description("productOptionDto.option1"),
                        fieldWithPath("productOptionDto.option2").type(JsonFieldType.STRING).description("productOptionDto.option2"),
                        fieldWithPath("productOptionDto.createdAt").type(JsonFieldType.STRING).description("productOptionDto.createdAt"),
                        fieldWithPath("productOptionDto.updatedAt").type(JsonFieldType.STRING).description("productOptionDto.updatedAt")
                    ),
                    responseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("count"),
                        fieldWithPath("productOptionDto").type(JsonFieldType.OBJECT).description("productOptionDto"),
                        fieldWithPath("productOptionDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.id"),
                        fieldWithPath("productOptionDto.productDto").type(JsonFieldType.OBJECT).description("productOptionDto.productDto"),
                        fieldWithPath("productOptionDto.productDto.id").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.id"),
                        fieldWithPath("productOptionDto.productDto.name").type(JsonFieldType.STRING).description("productOptionDto.productDto.name"),
                        fieldWithPath("productOptionDto.productDto.price").type(JsonFieldType.NUMBER).description("productOptionDto.productDto.price"),
                        fieldWithPath("productOptionDto.productDto.status").type(JsonFieldType.STRING).description("productOptionDto.productDto.status"),
                        fieldWithPath("productOptionDto.productDto.thumbnailImgPath").type(JsonFieldType.STRING).description("productOptionDto.productDto.thumbnailImgPath"),
                        fieldWithPath("productOptionDto.stock").type(JsonFieldType.NUMBER).description("productOptionDto.stock"),
                        fieldWithPath("productOptionDto.option1").type(JsonFieldType.STRING).description("productOptionDto.option1"),
                        fieldWithPath("productOptionDto.option2").type(JsonFieldType.STRING).description("productOptionDto.option2"),
                        fieldWithPath("productOptionDto.createdAt").type(JsonFieldType.STRING).description("productOptionDto.createdAt"),
                        fieldWithPath("productOptionDto.updatedAt").type(JsonFieldType.STRING).description("productOptionDto.updatedAt")
                    )
                )
            );
    }

}