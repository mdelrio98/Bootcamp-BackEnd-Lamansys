package ar.lamansys.cart.infrastructure.output.repository.product.database.repository;

import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductJPARepository extends JpaRepository<ProductJPAEntity,Long> {


}
