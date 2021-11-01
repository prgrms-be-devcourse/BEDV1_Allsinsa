package com.progm.allsinsa.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequestDto {
    private String name;
    private int price;
    private String category;
    private String status;
    private String productDetailImgPath;
    private String thumbnailImgPath;
    private long sellerId;
}
