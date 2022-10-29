package ar.lamansys.cart.infrastructure.output.repository.cart.database.entity;

import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products_in_carts")
@AllArgsConstructor
@NoArgsConstructor
public class ProductInCartJPAEntity {

    @EmbeddedId
    private ProductInCartKey id;

    @ManyToOne
    @MapsId("products")
    @JoinColumn(name = "product_id")
    private ProductJPAEntity product;

    @ManyToOne
    @MapsId("carts")
    @JoinColumn(name = "cart_id")
    private CartJPAEntity cart;

    private Integer quantity;

}
