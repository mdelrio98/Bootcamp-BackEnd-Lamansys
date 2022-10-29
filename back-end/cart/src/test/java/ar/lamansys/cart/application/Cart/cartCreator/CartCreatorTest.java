package ar.lamansys.cart.application.Cart.cartCreator;

import ar.lamansys.cart.application.Cart.CartValidations;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartCreatorTest {
    @Mock
    private CartStorage cartStorage;

    @Mock
    private ProductStorage productStorage;

    @Mock
    private CartValidations cartValidations;

    @InjectMocks
    private CartCreator cartCreator;
    @Test
    void CreatorUseCaseTest() {
        //carrito y producto bien creado
        //Arrange
        ProductBO pc = new ProductBO(2L, 5,10F);

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(5L, 4);

        CartBO cart = new CartBO(4L, products,false);

        when(cartStorage.save(cart)).thenReturn(4L);

        //Act
        var id = cartCreator.run(cart, pc);

        //Assert
        verify(cartValidations,times(1)).validateCartNotExists(cart.getUserId());
        verify(cartValidations,times(1)).validateProduct(pc);
        verify(cartStorage, times(1)).save(cart);

        assertThat(cart.getUserId()).isEqualTo(id);
    }
}