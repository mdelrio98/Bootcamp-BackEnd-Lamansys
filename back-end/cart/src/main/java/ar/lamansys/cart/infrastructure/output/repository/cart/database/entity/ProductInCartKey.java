package ar.lamansys.cart.infrastructure.output.repository.cart.database.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ProductInCartKey implements Serializable {
// Clase/tabla que uso para generar una clave compuesta
    @Column(name = "product_id")
    Long product_id;

    @Column(name = "cart_id")
    Long cart_id;
}
