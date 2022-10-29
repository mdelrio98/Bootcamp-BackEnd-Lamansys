package ar.lamansys.cart.infrastructure.output.repository.cart.inmemmory.entities;

import ar.lamansys.cart.domain.Cart.CartBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    private Long id;

    private HashMap<Long,Integer> productsCart = new HashMap<>();

    boolean isProcessed;
    public CartBO toCartBO(){
        return  new CartBO(this.id,this.productsCart,this.isProcessed);
    }
}
