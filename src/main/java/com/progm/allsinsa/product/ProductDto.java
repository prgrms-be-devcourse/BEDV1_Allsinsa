package com.progm.allsinsa.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
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
