package ar.lamansys.cart.application.Cart.cartCheckout;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartCreator.CartCreator;
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
class CartCheckoutTest {
    @Mock
    private CartStorage cartStorage;

    @Mock
    private ProductStorage productStorage;

    @InjectMocks
    private CartCheckout cartCheckout;

    @Mock
    private CartValidations cartValidations;

    @Test
    void CartCheckoutUseCaseTest() {
        //arrange

        HashMap<Long,Integer> products = new HashMap<>();
        products.put(5L,10);

        CartBO cart = new CartBO(4L, products,false);

        when(cartStorage.findByUserId(4L)).thenReturn(Optional.of(cart));

        //Act
        var cart2 = cartCheckout.run(4L);

        //assert

        verify(cartValidations,times(1)).validateCartExists(cart2.getUserId());
        verify(cartValidations,times(1)).validateIsProcessed(cart2.getUserId());
        verify(productStorage,times(1)).checkoutCart(cart2.getProductBOList());
        verify(cartStorage,times(1)).save(cart2);

        assertThat(cart2.getIsProcessed());
    }
}