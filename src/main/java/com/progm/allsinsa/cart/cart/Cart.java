package com.progm.allsinsa.cart.cart;

import com.progm.allsinsa.cart.cartProduct.CartProduct;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // TODO : Member
    //@OneToOne(
    //@JoinColumn(name = "member_id", referencedColumnName = "id")
    //private Member member
    @Column(name="member_id", nullable = false)
    private Long member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProducts = new ArrayList<>();

    // construct
    public Cart(Long member) {
        this.member = member;
    }

    // Mapping
    // TODO : Member
    /*public void setMember(Member member) {
        if(Objects.nonNull(this.member)) {
            member.getOrders().remove(this);
        }
        this.member = member;
        member.getCart().add(this);
    }*/

    public void addCartProduct(CartProduct cartProduct) {
        cartProduct.setCart(this);
    }

}
