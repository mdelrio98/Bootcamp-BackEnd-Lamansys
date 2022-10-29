package ar.lamansys.cart.application.Cart.cartProductDeletter;

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
 *  Caso de uso Eliminar producto del carrito de compras
 *  Primero traigo el carrito del usuario en caso de que lo tenga
 *  Segundo hago las validaciones necesarias
 *  Le quito la cantidad de productos requeridas y persisto
 *  Lo devuelvo
 * * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartProductDeletter {
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;

    @Autowired
    private CartValidations cartValidations;

    public CartBO run(Long id, final ProductBO productBO){

        Optional<CartBO> cart = cartStorage.findByUserId(id);
        cartValidations.validateCartExists(id);
        cartValidations.validateIsProcessed(cart.get().getUserId());
        cartValidations.validateProduct(productBO);
        cartValidations.validateProductDeletter(cart.get(),productBO);
        cartStorage.removeProduct(cart,productBO);
        return new CartBO(cart.get().getUserId(),cart.get().getProductBOList(),cart.get().getIsProcessed());
    }
}
