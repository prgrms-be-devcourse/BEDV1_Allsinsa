package com.progm.allsinsa.product;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductRequestDto {
    private String name;
    private int price;
    private String category;
    private String status;
    private String productDetailImgPath;
    private String thumbnailImgPath;
    private long sellerId;
}
