package ar.lamansys.cart.application.Cart.cartCheckout;

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
*  Caso de uso Finalizar Compra
*  Primero hago las validaciones necesarias para poder finalizar la compra
*  Luego hago la baja en el stock de productos almacenados en la BD.
*  Seteo que el carrito esta procesado
*  Y por lo ultimo persisto el carrito en la base
* * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartCheckout {
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;
    @Autowired
    private CartValidations cartValidations;

    public CartBO run(Long id) {
        Optional<CartBO> cart = cartStorage.findByUserId(id);
        cartValidations.validateCartExists(cart.get().getUserId());
        cartValidations.validateIsProcessed(cart.get().getUserId());
        for(Long i : cart.get().getProductBOList().keySet()){
            productStorage.getProductById(i).ifPresent(p-> cartValidations.validateProduct(p));
        }
        productStorage.checkoutCart(cart.get().getProductBOList());
        cart.get().setIsProcessed(true);
        cartStorage.save(cart.get());
        return new CartBO(cart.get().getUserId(),cart.get().getProductBOList(),cart.get().getIsProcessed());
    }
}
