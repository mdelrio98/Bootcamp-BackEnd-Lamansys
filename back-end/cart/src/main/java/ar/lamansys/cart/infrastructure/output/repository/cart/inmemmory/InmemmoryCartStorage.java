package ar.lamansys.cart.infrastructure.output.repository.cart.inmemmory;

import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.cart.inmemmory.entities.CartEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InmemmoryCartStorage implements CartStorage {

    private Map<Long, CartEntity> dataCart = new HashMap<>();

    public InmemmoryCartStorage() {
        // hago 3 hash de productos
        HashMap<Long,Integer> p1 = new HashMap<>();
        p1.put(3L,34);
        HashMap<Long,Integer> p2 = new HashMap<>();
        p2.put(2L,52);
        p2.put(3L,11);
        HashMap<Long,Integer> p3 = new HashMap<>();
        p3.put(1L,4);

        //Precargue 3 carritos
        CartEntity ce1 =new CartEntity(1L,p1,false);
        CartEntity ce2 =new CartEntity(2L,p2,false);
        CartEntity ce3 =new CartEntity(3L,p3,false);
        this.dataCart.put(ce1.getId(), ce1);
        this.dataCart.put(ce2.getId(), ce2);
        this.dataCart.put(ce3.getId(), ce3);
    }

    @Override
    public Optional<CartBO> findByUserId(Long id) {
        if(dataCart.containsKey(id))
            return Optional.of( dataCart.get(id).toCartBO() );
        return Optional.empty();
    }

    @Override
    public Long save(CartBO cart) {

        dataCart.put(cart.getUserId(), new CartEntity(cart.getUserId(),cart.getProductBOList(),cart.getIsProcessed()));
        return cart.getUserId();
    }

    @Override
    public CartBO removeProduct(Optional<CartBO> cart, ProductBO productBO) {
        //Lo agregue cuando hice impl en bd
        return null;
    }
}
