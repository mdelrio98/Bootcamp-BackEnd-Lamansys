package ar.lamansys.cart.application.Cart.cartPrice;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/*
 *  Caso de uso Calcular costo total carrito
 *  Primero traigo el carrito del usuario en caso de que lo tenga
 *  Segundo hago las validaciones necesarias
 *  Luego hago el calculo total del carrito multiplicando cada precio por unidad por su cantidad
 *  Por ultimo los persisto
 * * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Service
public class CartPrice {
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;

    @Autowired
    private CartValidations cartValidations;

    public Float run(Long id){
        Optional<CartBO> cart = cartStorage.findByUserId(id);
        CartBO cart2 = new CartBO(cart.get().getUserId(),cart.get().getProductBOList(),cart.get().getIsProcessed());
        cartValidations.validateCartExists(id);
        Float price = 0F;
        for (Map.Entry<Long, Integer> entry : cart2.getProductBOList().entrySet()) {
            var product = productStorage.getProductById(entry.getKey()).get();
            if (product.getQuantity() >= entry.getValue()) {
                price += product.getUnitPrice() * entry.getValue();
            }
        }
        cartStorage.save(cart2);
        return price;
    }
}
