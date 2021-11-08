package com.progm.allsinsa.order.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.progm.allsinsa.global.domain.BaseTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

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

    public Order(Long memberId, String recipientName, String phoneNumber, String shippingAddress, String memo,
            int totalAmount) {
        this.memberId = memberId;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.orderNumber = createOrderNumber();
        this.shippingAddress = shippingAddress;
        this.memo = memo;
        this.totalAmount = totalAmount;
        this.orderStatus = OrderStatus.COMPLETE;
    }

    private String createOrderNumber() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + UUID.randomUUID();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
    }
}
