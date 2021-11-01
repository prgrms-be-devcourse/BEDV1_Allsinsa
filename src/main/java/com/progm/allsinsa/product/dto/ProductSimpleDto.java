package com.progm.allsinsa.product.dto;

import com.progm.allsinsa.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ProductSimpleDto {
    private Long id;
    private String name;
    private int price;
    private String status;
    private String thumbnailImgPath;

    public static ProductSimpleDto from(Product product) {
        return new ProductSimpleDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStatus(),
                product.getThumbnailImgPath());
    }
}
