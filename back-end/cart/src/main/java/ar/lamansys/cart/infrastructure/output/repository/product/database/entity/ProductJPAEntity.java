package ar.lamansys.cart.infrastructure.output.repository.product.database.entity;

import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductJPAEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Float price;

    @OneToMany(mappedBy = "product")
    private Set<ProductInCartJPAEntity> carts;

    public ProductJPAEntity(ProductBO productBO) {
        this.id = productBO.getId();
        this.price = productBO.getUnitPrice();
        this.quantity = productBO.getQuantity();

    }
    public ProductJPAEntity(Long id, Integer quantity, Float price) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductBO toProductBO(){
        return new ProductBO(id,quantity,price);
    }

}
