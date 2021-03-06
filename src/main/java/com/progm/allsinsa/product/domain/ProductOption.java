package com.progm.allsinsa.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.progm.allsinsa.global.domain.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductOption extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int stock;

    @Column(length = 100)
    private String option1;

    @Column(length = 100)
    private String option2;

    @Builder
    public ProductOption(Product product, int stock, String option1, String option2) {
        this.product = product;
        this.stock = stock;
        this.option1 = option1;
        this.option2 = option2;
    }

    public void updateOptionName(String option1, String option2) {
        this.option1 = option1;
        this.option2 = option2;
    }

    public int purchaseProductOption(int purchasedNum) {
        if (stock >= purchasedNum) {
            stock -= purchasedNum;
        } else {
            throw new IllegalArgumentException("재고가 부족합니다. product option " + id + "의 재고: " + stock);
        }

        return stock;
    }

    public int addStock(int additionalStock) {
        return stock += additionalStock;
    }
}
