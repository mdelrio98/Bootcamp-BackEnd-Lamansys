package ar.lamansys.cart.application.Ports;

import ar.lamansys.cart.domain.Product.ProductBO;

import java.util.HashMap;
import java.util.Optional;

public interface ProductStorage {
    void save(ProductBO productStockBO);
    Optional<ProductBO> getProductById(Long id);
    HashMap<Long,Integer> getAllProducts(HashMap<Long,Integer> products);

    void checkoutCart(HashMap<Long,Integer> products);

}
