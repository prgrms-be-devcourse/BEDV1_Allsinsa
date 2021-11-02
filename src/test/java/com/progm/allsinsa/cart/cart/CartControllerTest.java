package com.progm.allsinsa.cart.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class CartControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CartService cartService;

}