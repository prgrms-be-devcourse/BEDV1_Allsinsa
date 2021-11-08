package com.progm.allsinsa.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.progm.allsinsa.global.domain.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "product_detail_img_path", length = 500)
    private String productDetailImgPath;

    @Column(name = "thumbnail_img_path", length = 500)
    private String thumbnailImgPath;

    @Column(name = "seller_id")
    private long sellerId;

    @Builder
    public Product(String name, int price, String category, String status, String productDetailImgPath,
            String thumbnailImgPath, long sellerId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.status = status;
        this.productDetailImgPath = productDetailImgPath;
        this.thumbnailImgPath = thumbnailImgPath;
        this.sellerId = sellerId;
    }

    public Product(String name) {
        this.name = name;
    }
}
