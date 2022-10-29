package ar.lamansys.cart.infrastructure.output.repository.product.database.repository;

import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
@Primary
@AllArgsConstructor
public class ProductDatabaseImpl implements ProductStorage {

    private ProductJPARepository productJPARepository;

    @Override
    public void save(ProductBO productStockBO) {
        productJPARepository.save(new ProductJPAEntity(productStockBO));
    }

    @Override
    public Optional<ProductBO> getProductById(Long id) {
        if(productJPARepository.existsById(id)){
            return Optional.of(productJPARepository.getById(id).toProductBO());
        }
        return Optional.empty();
    }

    @Override
    public HashMap<Long, Integer> getAllProducts(HashMap<Long, Integer> products) {
        HashMap<Long, Integer> productsBO = new HashMap<>();
        products.forEach((k,v) -> {
            if (products.containsKey(k)) {
                productsBO.put(k, productJPARepository.getById(k).getQuantity());
            }
        });
        return productsBO;
    }

    @Override
    public void checkoutCart(HashMap<Long, Integer> products) {
        products.forEach((k,v) -> {
            if (productJPARepository.existsById(k)) {
                ProductJPAEntity productJPAEntity = productJPARepository.getById(k);
                Integer actualQuantity = productJPAEntity.getQuantity() - v;
                productJPARepository.save(new ProductJPAEntity(k,actualQuantity, productJPAEntity.getPrice()));
            }
        });
    }
}
