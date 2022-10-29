package ar.lamansys.cart.infrastructure.output.repository.cart.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
public class CartJPAEntity {

    @Id
    private Long user_id;
    @Column(name="processed")
    private boolean processed;

    @OneToMany(mappedBy = "cart")
    private Set<ProductInCartJPAEntity> products;

    public CartJPAEntity(Long userId, boolean processed) {
        this.user_id = userId;
        this.processed = processed;
        this.products = new HashSet<>();
    }
}
