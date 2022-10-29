package ar.lamansys.cart.infrastructure.input.rest.cart;

import ar.lamansys.cart.application.Cart.CartValidations;
import ar.lamansys.cart.application.Cart.cartCheckout.CartCheckout;
import ar.lamansys.cart.application.Cart.cartCreator.CartCreator;
import ar.lamansys.cart.application.Cart.cartGetter.CartGetter;
import ar.lamansys.cart.application.Cart.cartPrice.CartPrice;
import ar.lamansys.cart.application.Cart.cartProductAdder.CartProductAdder;
import ar.lamansys.cart.application.Cart.cartProductDeletter.CartProductDeletter;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.infrastructure.input.rest.cart.adapter.CartAdapter;
import ar.lamansys.cart.infrastructure.input.rest.cart.adapter.ProductAdapter;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.request.ProductDTO;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.response.CartDTOResponse;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.response.CartDTOResponseWithProcessed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartAdapter cartAdapter;
    @Autowired
    private ProductAdapter productAdapter;
    @Autowired
    private CartCreator cartCreator;
    @Autowired
    private CartGetter cartGetter;
    @Autowired
    private CartProductAdder cartProductAdder;

    @Autowired
    private CartProductDeletter cartProductDeletter;
    @Autowired
    private CartPrice cartPrice;
    @Autowired
    private CartCheckout cartCheckout;

    // POST CREAR CARRITO
    @PostMapping("/{userId}")
    public Long createCart(@PathVariable final Long userId, @RequestBody ProductDTO productCartDTO){
        CartBO cartBO = new CartBO(userId,new HashMap<Long,Integer>(),false);
        cartCreator.run(cartBO,productAdapter.toProductBO(productCartDTO));
        return cartBO.getUserId();
    }

    // GET VER CARRITO
    @GetMapping("/{userId}")
    public CartDTOResponse getCart(@PathVariable final Long userId){
        CartBO cartResponse = cartGetter.run(userId);
        return cartAdapter.toCartDTOResponse(cartResponse);
    }

    // POST AGREGAR PRODUCTOS AL CARRITO
    @PostMapping("/products/{userId}")
    public CartDTOResponse addProducts(@PathVariable final Long userId,@RequestBody ProductDTO productDTO){
        return cartAdapter.toCartDTOResponse(cartProductAdder.run(userId,productAdapter.toProductBO(productDTO)));
    }

    // DELETE ELIMINAR PRODUCTOS DEL CARRITO
    @DeleteMapping("/{userId}")
    public CartDTOResponse deleteProduct(@PathVariable final Long userId,@RequestBody ProductDTO productDTO){
        return cartAdapter.toCartDTOResponse(cartProductDeletter.run(userId,productAdapter.toProductBO(productDTO)));
    }

    // GET CALCULAR COSTO DE CARRITO
    @GetMapping("/price/{userId}")
    public Float getCartPrice(@PathVariable final Long userId){
        return cartPrice.run(userId);
    }

    // GET FINALIZAR COMPRA
    @GetMapping("/checkout/{userId}")
    public CartDTOResponseWithProcessed getCartCheckout(@PathVariable Long userId) {
        return cartAdapter.toCartDTOResponseWithProcessed(cartCheckout.run(userId));
    }
}



