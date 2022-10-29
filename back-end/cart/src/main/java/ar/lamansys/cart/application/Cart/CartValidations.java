package ar.lamansys.cart.application.Cart;

import ar.lamansys.cart.application.Cart.cartCreator.exception.CartCreatorException;
import ar.lamansys.cart.application.Cart.cartCreator.exception.CodeCartCreatorException;
import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 *  Genero todas las validaciones en una misma clase para no repetir codigo
 * * */
@NoArgsConstructor
@Service
public class CartValidations {
// No termino de saber si esta clase esta bien aca o tendria que ir en domain
    @Autowired
    private CartStorage cartStorage;

    @Autowired
    private ProductStorage productStorage;

    public void validateIsProcessed(Long id){
        if (cartStorage.findByUserId(id).get().getIsProcessed())  {
            //chequea que el carrito no este procesado
            throw new CartCreatorException(
                    CodeCartCreatorException.CART_IS_PROCESSED,
                    String.format("Cart (%d) is processed", id));
        }
    }
    public void validateCartNotExists(Long id){//el de cartcreator
        if (!cartStorage.findByUserId(id).isEmpty())  {
            //chequea que el usuario no tenga ya un carrito
            throw new CartCreatorException(
                    CodeCartCreatorException.CART_ALREADY_EXISTS,
                    String.format("User with given id (%d) has already a cart", id));
        }
    }

    public void validateCartExists(Long id){//el de cartProductAdder
        if (cartStorage.findByUserId(id).isEmpty())  {
            //chequea que el usuario ya tiene un carrito
            throw new CartCreatorException(
                    CodeCartCreatorException.CART_NOT_EXISTS,
                    String.format("User with given id (%d) does not have a cart", id));
        }
    }

    public void validateProduct( ProductBO product){
        Optional<ProductBO> productSt = productStorage.getProductById(product.getId());
        if(productSt.isEmpty()) {
            // valido si existe este producto en stock
            throw new CartCreatorException(
                    CodeCartCreatorException.PRODUCT_ID_NOT_FOUND,
                    String.format("Product with given id (%d) not exist", product.getId())
            );
        }
        if((product.getQuantity() < 1) || (productSt.get().getQuantity() < product.getQuantity())){
            //valido que la cantidad sea mayor a cero
            // valido si la cantidad que tengo en stock es suficiente para la cantidad que pide el usuario
            throw  new CartCreatorException(
                    CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE,
                    String.format("Quantity of product no available", product.getId())
            );
        }
    }
    public  void validateProductAdder(CartBO cart, ProductBO product){
        Optional<ProductBO> productSt = productStorage.getProductById(product.getId());
        if(cart.getProductBOList().containsKey(product.getId())){
            //chequeo que si tiene el producto el carrito no supere el stock en la suma de la cantidad ya pedida con la nueva
            if(productSt.get().getQuantity() < (product.getQuantity()+cart.getProductBOList().get(product.getId()))){
                throw  new CartCreatorException(
                        CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE,
                        String.format("Quantity of product no available", product.getId())
                );
            }
        }
    }

    public void validateProductDeletter(CartBO cart, ProductBO product){
        Optional<ProductBO> productSt = productStorage.getProductById(product.getId());
        if(cart.getProductBOList().containsKey(product.getId())) {
            //chequeo que si tiene el producto el carrito, en la resta no sea negativo
            if (cart.getProductBOList().get(product.getId()) - product.getQuantity() < 0) {
                throw new CartCreatorException(
                        CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE,
                        String.format("Quantity of product no available", product.getId())
                );
            }
            //chequeo que lo que tengo en stock sea mayor que lo que tengo en carrito mas lo pedido actualmente
            if (productSt.get().getQuantity() < (product.getQuantity() + cart.getProductBOList().get(product.getId()))) {
                throw new CartCreatorException(
                        CodeCartCreatorException.PRODUCT_QUANTITY_NOT_AVAILABLE,
                        String.format("Quantity of product no available", product.getId())
                );
            }
        }
    }
}
