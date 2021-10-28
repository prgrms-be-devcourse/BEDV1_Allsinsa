package com.progm.allsinsa.order.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    // 멤버 매핑으로 변경 예정
    // ManyToOne
    private Long memberId;

    @Column(length = 20, nullable = false)
    private String recipientName;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 255, nullable = false)
    private String shippingAddress;

    @Column(length = 500)
    private String memo;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private int savedAmount;

    @Column(nullable = false)
    private int paymentAmount;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Order(Long memberId, String recipientName, String phoneNumber, String shippingAddress, String memo, int totalAmount) {
        this.orderNumber = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
        this.memo = memo;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.COMPLETE;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
    }
}