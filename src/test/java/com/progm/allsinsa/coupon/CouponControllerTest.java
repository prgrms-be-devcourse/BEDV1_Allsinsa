package com.progm.allsinsa.coupon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progm.allsinsa.coupon.dto.CouponConverter;
import com.progm.allsinsa.coupon.dto.CouponDto;
import com.progm.allsinsa.coupon.dto.CouponInfoDto;
import com.progm.allsinsa.coupon.service.CouponInfoService;
import com.progm.allsinsa.member.domain.Member;
import com.progm.allsinsa.member.dto.MemberConverter;
import com.progm.allsinsa.member.dto.MemberDto;
import com.progm.allsinsa.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponInfoService couponInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createCouponInfoTest() throws Exception {
        CouponInfoDto couponInfoDto = new CouponInfoDto("크리스마스 쿠폰", "FIXED_AMOUNT_COUPON", 10000);

        mockMvc.perform(post("/api/v1/coupon-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponInfoDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("coupon-info-create", preprocessRequest(modifyUris().removePort(), prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("couponName"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("couponType"),
                                fieldWithPath("discountValue").type(JsonFieldType.NUMBER).description("discountValue")
                        ),
                        responseFields(
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("couponName"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("couponType"),
                                fieldWithPath("discountValue").type(JsonFieldType.NUMBER).description("discountValue")
                        )));
    }

    @Test
    public void issueCouponTest() throws Exception {
        CouponInfoDto couponInfoDto = new CouponInfoDto("크리스마스 쿠폰", "FIXED_AMOUNT_COUPON", 10000);
        Member sezikim = new Member("test1@gmail.com", "AaBb1234", "sezikim");
        couponInfoService.createCouponInfo(couponInfoDto);
        MemberDto sezikimDto = memberConverter.convertMemberDto(sezikim);
        MemberDto retrievedMemberDto = memberService.createMember(sezikimDto);
        CouponDto couponDto = new CouponDto(retrievedMemberDto, couponInfoDto, LocalDateTime.of(2022,1,1,0,0));

        mockMvc.perform(post("/api/v1/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("coupon-issue", preprocessRequest(modifyUris().removePort(), prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.id").type(JsonFieldType.NUMBER).description("memberDto.id"),
                                fieldWithPath("memberDto.email").type(JsonFieldType.STRING).description("memberDto.email"),
                                fieldWithPath("memberDto.password").type(JsonFieldType.STRING).description("memberDto.password"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("couponInfoDto").type(JsonFieldType.OBJECT).description("couponInfoDto"),
                                fieldWithPath("couponInfoDto.couponName").type(JsonFieldType.STRING).description("couponInfoDto.couponName"),
                                fieldWithPath("couponInfoDto.couponType").type(JsonFieldType.STRING).description("couponInfoDto.couponType"),
                                fieldWithPath("couponInfoDto.discountValue").type(JsonFieldType.NUMBER).description("couponInfoDto.discountValue"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("expiredAt")
                        ),
                        responseFields(
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.id").type(JsonFieldType.NUMBER).description("memberDto.id"),
                                fieldWithPath("memberDto.email").type(JsonFieldType.STRING).description("memberDto.email"),
                                fieldWithPath("memberDto.password").type(JsonFieldType.STRING).description("memberDto.password"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("couponInfoDto").type(JsonFieldType.OBJECT).description("couponInfoDto"),
                                fieldWithPath("couponInfoDto.couponName").type(JsonFieldType.STRING).description("couponInfoDto.couponName"),
                                fieldWithPath("couponInfoDto.couponType").type(JsonFieldType.STRING).description("couponInfoDto.couponType"),
                                fieldWithPath("couponInfoDto.discountValue").type(JsonFieldType.NUMBER).description("couponInfoDto.discountValue"),
                                fieldWithPath("expiredAt").type(JsonFieldType.STRING).description("expiredAt")
                        )));
    }
}
