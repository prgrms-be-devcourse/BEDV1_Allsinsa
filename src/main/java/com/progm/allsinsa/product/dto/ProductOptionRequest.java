package com.progm.allsinsa.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.progm.allsinsa.product.domain.Product;
import com.progm.allsinsa.product.domain.ProductOption;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductOptionRequest {
    @NotNull
    private ProductSimpleDto productDto;

    @Positive
    private int stock;

    @Length(max = 100)
    @NotBlank
    private String option1;

    private String option2;

    public ProductOption to(Product product) {
        return new ProductOption(product, stock, option1, option2);
    }
}
