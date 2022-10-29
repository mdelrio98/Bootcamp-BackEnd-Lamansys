package ar.lamansys.cart.infrastructure.output.repository.cart.database.repository;

import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.CartJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartJPARepository extends JpaRepository<CartJPAEntity,Long> {

    @Query("from CartJPAEntity cart where (cart.user_id = :user_id)")
    Optional<CartJPAEntity> findByUserId(@Param("user_id") Long user_id);

}
