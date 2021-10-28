package com.progm.allsinsa.cart.cartProduct;

import com.progm.allsinsa.cart.Cart;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="count", nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    // TODO : ProductOption
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="product_option_id", referencedColumnName = "id")
//    private ProductOption productOption;
    @Column(name="product_option_id", nullable = false)
    private Long productOption;

    // TODO : ProductOption
    public CartProduct(int count, Long productOption) {
        this.count = count;
        this.productOption = productOption;
    }

    public CartProduct(Long id, int count, Long productOption) {
        this(count, productOption);
        this.id = id;
    }


    public void setCart(Cart cart) {
        if(Objects.nonNull(this.cart)) {
            this.cart.getCartProducts().remove(this);
        }

        this.cart = cart;
        cart.getCartProducts().add(this);
    }

    // TODO : ProductOption
    /*
    public void setProductOption(ProductOption productOption) {
        if(Objects.nonNull(this.productOption)) {
            this.productOption.getCartProducts().remove(this);
        }

        this.productOption = productOption;
    }*/

}
