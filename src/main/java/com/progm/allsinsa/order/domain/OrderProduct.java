package com.progm.allsinsa.order.domain;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 100)
    private String productOption;

    @Column(nullable = false)
    private String thumbnailImagePath;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private Long productId;

    private Long productOptionId;

    public OrderProduct(String productName, int price, int quantity, String productOption, String thumbnailImagePath,
            long productId, long productOptionId) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productOption = productOption;
        this.thumbnailImagePath = thumbnailImagePath;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.orderStatus = OrderStatus.COMPLETE;
    }

    public void setOrder(Order order) {
        if (Objects.nonNull(this.order)) {
            this.order.getOrderProducts().remove(this);
        }

        this.order = order;
        order.getOrderProducts().add(this);
    }
}
