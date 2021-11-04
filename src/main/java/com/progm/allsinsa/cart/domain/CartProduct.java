package com.progm.allsinsa.cart.domain;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.progm.allsinsa.product.domain.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_option_id", referencedColumnName = "id")
    private ProductOption productOption;

    public CartProduct(int count, ProductOption productOption) {
        this.count = count;
        this.productOption = productOption;
    }

    public void setCart(Cart cart) {
        if (Objects.nonNull(this.cart)) {
            this.cart.getCartProducts().remove(this);
        }

        this.cart = cart;
        cart.getCartProducts().add(this);
    }

    public void setCount(int count) {
        this.count = count;
    }
}
