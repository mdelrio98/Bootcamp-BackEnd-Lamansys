package ar.lamansys.cart.infrastructure.output.repository.cart.database.repository;

import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.CartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCartJPAEntity, ProductInCartKey> {

}
