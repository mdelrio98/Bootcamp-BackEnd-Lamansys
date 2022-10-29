package ar.lamansys.cart.application.Cart.cartProductAdder;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/*
 *  Caso de uso Agregar productos al carrito
 *  Primero traigo el carrito del usuario en caso de que lo tenga
 *  Segundo hago las validaciones necesarias
 *  Agrego el producto al carrito
 *  Lo devuelvo
 * * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartProductAdder {
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;

    @Autowired
    private CartValidations cartValidations;

    public CartBO run(Long id, final ProductBO productBO){

        Optional<CartBO>  cart = cartStorage.findByUserId(id);
        CartBO cart2 = new CartBO(cart.get().getUserId(),cart.get().getProductBOList(),cart.get().getIsProcessed());
        cartValidations.validateCartExists(cart2.getUserId());
        cartValidations.validateIsProcessed(cart2.getUserId());
        cartValidations.validateProduct(productBO);
        cartValidations.validateProductAdder(cart2,productBO);
        cart.get().addProduct(productBO);
        cartStorage.save(cart2);
        return cart2;
    }


}
