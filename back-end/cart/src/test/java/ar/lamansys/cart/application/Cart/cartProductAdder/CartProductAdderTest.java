package ar.lamansys.cart.application.Cart.cartProductAdder;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartCreator.CartCreator;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CartCreatorException;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CodeCartCreatorException;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartProductAdderTest {

    @Mock
    private CartStorage cartStorage;

    @Mock
    private ProductStorage productStorage;

    @Mock
    private CartValidations cartValidations;

    @InjectMocks
    private CartProductAdder cartProductAdder;

    @Test
    void ProductAdderUseCaseTest() {
        //arrange

        ProductBO tabletStock = new ProductBO(5L,30,10F);
        ProductBO tablet = new ProductBO(5L,20,10F);

        HashMap<Long,Integer> products = new HashMap<>();
        products.put(5L,10);

        CartBO cart = new CartBO(4L, products,false);

        when(cartStorage.findByUserId(4L)).thenReturn(Optional.of(cart));

        //Act
        var cart2 = cartProductAdder.run(cart.getUserId(),tablet);

        //assert

        verify(cartValidations,times(1)).validateCartExists(cart.getUserId());
        verify(cartValidations,times(1)).validateProduct(tablet);
        verify(cartValidations,times(1)).validateProductAdder(cart,tablet);
        verify(cartStorage, times(1)).save(cart2);

        assertThat(cart2.getProductBOList())
                .isEqualTo(products);
    }
}