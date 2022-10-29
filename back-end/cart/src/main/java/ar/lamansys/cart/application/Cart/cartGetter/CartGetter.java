package ar.lamansys.cart.application.Cart.cartGetter;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CartCreatorException;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CodeCartCreatorException;
import ar.lamansys.cart.application.Cart.cartGetter.exception.CartGetterException;
import ar.lamansys.cart.application.Cart.cartGetter.exception.CodeCartGetterException;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/*
 *  Caso de uso Ver carrito de compras
 *  Primero traigo el carrito del usuario en caso de que lo tenga
 *  Segundo hago las validaciones necesarias para poder ver el carrito
 *  Lo devuelvo
 * * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartGetter {
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;

    @Autowired
    private CartValidations cartValidations;

    public CartBO run(Long id){
        Optional<CartBO> cartResponseOpt = cartStorage.findByUserId(id);
        cartValidations.validateCartExists(cartResponseOpt.get().getUserId());
        return  new CartBO(cartResponseOpt.get().getUserId(),cartResponseOpt.get().getProductBOList(),cartResponseOpt.get().getIsProcessed());
    }

}
