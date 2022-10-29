package ar.lamansys.cart.application.Cart.cartPrice;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartProductAdder.CartProductAdder;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartPriceTest {
    @Mock
    private CartStorage cartStorage;

    @Mock
    private ProductStorage productStorage;

    @Mock
    private CartValidations cartValidations;

    @InjectMocks
    private CartPrice cartPrice;

    @Test
    void CartPriceUseCaseTest() {
        //arrange

        ProductBO tabletStock = new ProductBO(5L,30,10F);

        HashMap<Long,Integer> products = new HashMap<>();
        products.put(5L,10);

        CartBO cart = new CartBO(4L, products,false);

        when(cartStorage.findByUserId(4L)).thenReturn(Optional.of(cart));
        when(productStorage.getProductById(5L)).thenReturn(Optional.of(tabletStock));

        //Act
        var price = cartPrice.run(cart.getUserId());

        //assert

        verify(cartValidations,times(1)).validateCartExists(cart.getUserId());

        assertThat(price != 100F);
    }
}