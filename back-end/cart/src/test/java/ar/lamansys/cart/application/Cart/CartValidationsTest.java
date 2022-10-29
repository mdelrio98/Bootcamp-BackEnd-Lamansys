package ar.lamansys.cart.application.Cart;

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

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartValidationsTest {

    @Mock
    private CartStorage cartStorage;

    @Mock
    private ProductStorage productStorage;

    @Mock
    private CartCreator cartCreator;

    @InjectMocks
    private CartValidations cartValidations;

    @Test
    void CartIsProcessedTest(){
        //Arrange
        ProductBO pc = new ProductBO(2L, 3,10F);

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(2L, 4);

        CartBO cart = new CartBO(4L, products,true);
        when(cartStorage.findByUserId(4L)).thenReturn(Optional.of(cart));
        //act
        var thrown = catchThrowable(
                () -> cartValidations.validateIsProcessed(cart.getUserId()) );

        Assertions.assertThat(thrown)
                .as("Check cart_is_processed")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.CART_IS_PROCESSED);
    }
    @Test
    void CartAlreadyExistExceptionTest(){
        //Arrange
        ProductBO pc = new ProductBO(2L, 3,10F);

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(2L, 4);

        CartBO cart = new CartBO(4L, products,false);
        when(cartStorage.findByUserId(4L)).thenReturn(Optional.of(cart));
        //act
        var thrown = catchThrowable(
                () -> cartValidations.validateCartNotExists(cart.getUserId()) );

        Assertions.assertThat(thrown)
                .as("Check cart_userid_already_exist")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.CART_ALREADY_EXISTS);

    }
    @Test
    void CartNotExistExceptionTest(){
        //Arrange
        ProductBO pc = new ProductBO(2L, 3,10F);

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(2L, 4);

        CartBO cart = new CartBO(4L, products,false);
        when(cartStorage.findByUserId(4L)).thenReturn(Optional.empty());
        //act
        var thrown = catchThrowable(
                () -> cartValidations.validateCartExists(4L) );

        Assertions.assertThat(thrown)
                .as("Check cart_userid_not_exists")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.CART_NOT_EXISTS);

    }
    @Test
    void QuantityNotAvailableExceptionTest(){
        //Arrange
        ProductBO pc = new ProductBO(2L,3,10F);//Lo que tengo
        ProductBO pc2 = new ProductBO(2L, 5,10F);;//Lo que pido

        ProductBO tablet = new ProductBO(3L, 7,10F);;//Lo que tengo
        ProductBO tablet2 = new ProductBO(3L, 0,10F);;//Lo que pido

        ProductBO phone = new ProductBO(1L, 30,10F);;//Lo que tengo
        ProductBO phone2 = new ProductBO(1L, 22,10F);;//Lo que pido agregar
        ProductBO phone3 = new ProductBO(1L, 12,10F);;//Lo que pido sacar

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(1L,10);//Lo que tengo en carrito

        CartBO cart = new CartBO(4L, products,false);

        when(productStorage.getProductById(1L)).thenReturn(Optional.of(phone));
        when(productStorage.getProductById(2L)).thenReturn(Optional.of(pc));
        when(productStorage.getProductById(3L)).thenReturn(Optional.of(tablet));
        //act
        var thrown = catchThrowable(
                () -> cartValidations.validateProduct(pc2));// caso quantity mayor que stock

        var thrown2 = catchThrowable(
                () -> cartValidations.validateProduct(tablet2) );// caso quantity menor o igual a cero

        var thrown3 = catchThrowable(
                () -> cartValidations.validateProductAdder(cart,phone2) );// caso quantity ya tiene producto y se pasa del stock agregando otro

        var thrown4 =catchThrowable(
                () -> cartValidations.validateProductDeletter(cart,phone3) );// caso quantity quiere sacar mas cantidad de la que tiene en carrito

        //assert
        Assertions.assertThat(thrown)
                .as("Check product_quantity_not_available")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE);

        Assertions.assertThat(thrown2)
                .as("Check product_quantity_not_available")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE);

        Assertions.assertThat(thrown3)
                .as("Check product_quantity_not_available")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE);

        Assertions.assertThat(thrown4)
                .as("Check product_quantity_not_available")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE);
    }


    @Test
    void ProductNotExistExceptionTest(){
        //Arrange
        ProductBO pc = new ProductBO(2L, 3,10F);
        ProductBO pc2 = new ProductBO(3L, 3,10F);

        HashMap<Long, Integer> products = new HashMap<>();
        products.put(0L, 4);

        CartBO cart = new CartBO(4L, products,false);
        when(productStorage.getProductById(2L)).thenReturn(Optional.empty());
        when(productStorage.getProductById(3L)).thenReturn(Optional.of(pc2));//para el caso del deletter que no tenga el producto que quiere sacar en el carrito
        //act
        var thrown = catchThrowable(
                () -> cartValidations.validateProduct(pc));

        var thrown2 = catchThrowable(
                () -> cartValidations.validateProductDeletter(cart,pc2));

        Assertions.assertThat(thrown)
                .as("Check product_not_exist")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_ID_NOT_FOUND);

        Assertions.assertThat(thrown2)
                .as("Check product_not_exist")
                .isInstanceOf(CartCreatorException.class)
                .extracting("code")
                .isEqualTo(CodeCartCreatorException.PRODUCT_ID_NOT_FOUND);
    }
}