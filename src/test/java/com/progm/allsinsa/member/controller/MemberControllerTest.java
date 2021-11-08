package com.progm.allsinsa.member.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.cart.repository.CartRepository;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.repository.MemberRepository;
import com.progm.allsinsa.member.service.MemberService;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    private String email;
    private String password;
    private String name;
    private MemberDto memberDto;

    private String url;

    @BeforeEach
    void setUp() {
        cartRepository.deleteAll();
        memberRepository.deleteAll();

        email = "trip@gmail.com";
        password = "12345678Abc";
        name = "앨런";
        memberDto = MemberDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        url = "/api/v1/members";
    }

    @Test
    @DisplayName("회원 생성")
    void create() throws Exception {
        // given
        String requestBody = objectMapper.writeValueAsString(memberDto);

        // when
        mockMvc.perform(post(url)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-member",
                        preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("member id").ignored(),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ))
                );
    }

    @Test
    @DisplayName("아이디로 회원 조회")
    void findMemberById() throws Exception {
        // given
        MemberDto member = memberService.createMember(memberDto);
        Long memberId = member.getId();

        // when
        mockMvc.perform(get(url + "/" + memberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-member-by-id",
                        preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ))
                );
    }

    @Test
    @DisplayName("이메일로 회원 조회")
    void findMemberByEmail() throws Exception {
        // given
        memberService.createMember(memberDto);

        // when
        mockMvc.perform(get(url)
                        .param("email", email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-member-by-email",
                        preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ))
                );
    }

    @Test
    @DisplayName("회원 정보 수정")
    void update() throws Exception {
        // given
        MemberDto member = memberService.createMember(memberDto);
        Long memberId = member.getId();

        MemberDto updateMemberDto = MemberDto.builder()
                .id(memberId)
                .email(memberDto.getEmail())
                .name("홍빈")
                .password("zyX09876543")
                .build();
        String requestBody = objectMapper.writeValueAsString(updateMemberDto);

        // when
        mockMvc.perform(put(url + "/" + memberId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-member",
                        preprocessRequest(modifyUris().removePort(), prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("member id").ignored(),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email").ignored(),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name")
                        ))
                );
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() throws Exception {
        // given
        MemberDto member = memberService.createMember(memberDto);
        Long memberId = member.getId();

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete(url + "/" + memberId))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("delete-member",
                                preprocessRequest(modifyUris().removePort(), prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }
}
