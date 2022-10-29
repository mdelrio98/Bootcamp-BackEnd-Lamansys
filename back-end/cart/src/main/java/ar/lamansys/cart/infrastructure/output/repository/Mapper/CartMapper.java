package ar.lamansys.cart.infrastructure.output.repository.Mapper;

import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.CartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartBO toCartBO(CartJPAEntity cartEntity){
        HashMap<Long,Integer> productsMap = new HashMap<>();
        for(ProductInCartJPAEntity prodInCart : cartEntity.getProducts()) {
            productsMap.put(prodInCart.getProduct().getId(), prodInCart.getQuantity());
        }
        return new CartBO(cartEntity.getUser_id(),productsMap,cartEntity.isProcessed());
    }

    public static CartJPAEntity toCartJPAEntity(CartBO cart, boolean processed){
        return new CartJPAEntity(cart.getUserId(), processed);
    }
}
