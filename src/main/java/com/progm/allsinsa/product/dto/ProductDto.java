package com.progm.allsinsa.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
    private long id;
    private String name;
    private int price;
    private String category;
    private String status;
    private String productDetailImgPath;
    private String thumbnailImgPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long sellerId;
}
