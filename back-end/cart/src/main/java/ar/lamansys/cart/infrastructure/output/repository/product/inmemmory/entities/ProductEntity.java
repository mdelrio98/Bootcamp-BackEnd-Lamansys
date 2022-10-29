package ar.lamansys.cart.infrastructure.output.repository.product.inmemmory.entities;

import ar.lamansys.cart.domain.Product.ProductBO;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    private Long id;
    private Integer quantity;

    private Float unitPrice;

    public ProductEntity(ProductBO productBO){
        this.id= productBO.getId();
        this.quantity= productBO.getQuantity();
        this.unitPrice = productBO.getUnitPrice();
    }

    public ProductBO toProductBO() {
        return new ProductBO(this.id,this.quantity,this.getUnitPrice());
    }

}
