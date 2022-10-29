package ar.lamansys.cart.application.Ports;

import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;

import java.util.Optional;


public interface CartStorage {

    Optional<CartBO> findByUserId(Long id);

    //TODO CAMBIE EL VOID A LONG EN SAVE
    Long save(CartBO cart);

    CartBO removeProduct(Optional<CartBO> cart, ProductBO productBO);
}
