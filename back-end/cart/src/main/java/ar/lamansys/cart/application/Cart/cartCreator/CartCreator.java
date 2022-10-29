package ar.lamansys.cart.application.Cart.cartCreator;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CartCreatorException;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CodeCartCreatorException;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 *  Caso de uso Crear Carrito de Compras
 *  Primero hago las validaciones necesarias para poder crear el carrito
 *  Luego le cargo el producto en el carrito
 *  y por ultimo lo persisto
 * * */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartCreator {

    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;


    @Autowired
    private CartValidations cartValidations;


    public Long run(final CartBO cart, final ProductBO product) {

        cartValidations.validateCartNotExists(cart.getUserId());
        cartValidations.validateProduct(product);

        cart.addProduct(product);// ahora si guardo el producto
        //guardo el carrito

        return cartStorage.save(cart);
    }
}
