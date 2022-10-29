package ar.lamansys.cart.domain.Product;

import ar.lamansys.cart.domain.DomainException;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ProductBO {
    private Long id;
    private Integer quantity;
    private Float unitPrice;
    public ProductBO(Long id,Integer quantity, Float unitPrice){
        validateId(id);
        this.id = id;
        this.quantity = quantity;
        this.unitPrice =unitPrice;
    }
    public void validateId(Long id) {
        if (id == null) {
            throw new DomainException("cart_id_not_null", "The cart id cannot be null.");
        }
    }
}
