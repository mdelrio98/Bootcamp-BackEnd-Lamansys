package ar.lamansys.cart.domain.Cart;

import ar.lamansys.cart.domain.DomainException;
import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CartBO {
    private Long userId;
    private HashMap<Long,Integer> productBOList;

    private Boolean isProcessed;

    public CartBO(Long userId, HashMap<Long,Integer> productCartBOList,Boolean isProcessed){
        validateId(userId);
        this.userId = userId;
        this.productBOList = productCartBOList;
        this.isProcessed = isProcessed;
    }
    public CartBO(Long userId){
        validateId(userId);
        this.userId = userId;
        this.productBOList = new HashMap<>();
    }
    public void validateId(Long id) {
        if (id == null) {
            throw new DomainException("cart_id_not_null", "The cart id cannot be null.");
        }
    }

    public void addProduct(ProductBO productBO){
        if(productBOList.containsKey(productBO.getId())){
            productBOList.replace(productBO.getId(),productBOList.get(productBO.getId()) + productBO.getQuantity());
        }else productBOList.put(productBO.getId(),productBO.getQuantity());
    }



    @Override
    public boolean equals(Object o) {//Para solucionar las comparaciones en los test
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartBO cartBO = (CartBO) o;

        return userId != null ? userId.equals(cartBO.userId) : cartBO.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
