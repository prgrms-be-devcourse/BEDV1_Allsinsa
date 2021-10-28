package com.progm.allsinsa.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product {
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

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "seller_id")
    private long sellerId;


    public Product(String name, int price, String category, String status, String productDetailImgPath, String thumbnailImgPath, LocalDateTime createdAt, LocalDateTime updatedAt, long sellerId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.status = status;
        this.productDetailImgPath = productDetailImgPath;
        this.thumbnailImgPath = thumbnailImgPath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sellerId = sellerId;
    }

    public Product(String name, int price, String category, String status, String productDetailImgPath, String thumbnailImgPath, long sellerId) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.status = status;
        this.productDetailImgPath = productDetailImgPath;
        this.thumbnailImgPath = thumbnailImgPath;
        this.sellerId = sellerId;
    }
}
