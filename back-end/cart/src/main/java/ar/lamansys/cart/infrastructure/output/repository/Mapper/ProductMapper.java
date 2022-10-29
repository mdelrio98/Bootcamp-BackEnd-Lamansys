package ar.lamansys.cart.infrastructure.output.repository.Mapper;

import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;

public class ProductMapper {
    public static ProductBO toProductBO(ProductInCartJPAEntity prod){
        return new ProductBO(prod.getId().getProduct_id(), prod.getQuantity(),prod.getProduct().getPrice());
    }
    public static ProductBO toProductBO(ProductJPAEntity prod){
        return new ProductBO(prod.getId(), prod.getQuantity(), prod.getPrice());
    }

    public static ProductJPAEntity toProductEntity(ProductBO prod, Integer quantity){
        return new ProductJPAEntity(prod.getId(), quantity,prod.getUnitPrice());
    }
}
