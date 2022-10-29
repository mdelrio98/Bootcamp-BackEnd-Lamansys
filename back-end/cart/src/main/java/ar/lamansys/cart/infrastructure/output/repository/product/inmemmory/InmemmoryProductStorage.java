package ar.lamansys.cart.infrastructure.output.repository.product.inmemmory;

import ar.lamansys.cart.application.Ports.ProductStorage;
import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.product.inmemmory.entities.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class InmemmoryProductStorage implements ProductStorage {

    private Map<Long, ProductEntity> productStock= new HashMap<>();

    public InmemmoryProductStorage() {
        //Precargue 3 productos de stock
        ProductEntity pse1 =new ProductEntity(1L,50,33F);
        ProductEntity pse2 =new ProductEntity(2L,100,8F);
        ProductEntity pse3 =new ProductEntity(3L,150,12F);
        this.productStock.put(pse1.getId(), pse1);
        this.productStock.put(pse2.getId(), pse2);
        this.productStock.put(pse3.getId(), pse3);
    }

    @Override
    public void save(ProductBO productBO) {
        productStock.put(
                productBO.getId(),
                new ProductEntity(productBO));

    }

    @Override
    public Optional<ProductBO> getProductById(Long id) {
        if(productStock.containsKey(id)){
            return Optional.of(productStock.get(id).toProductBO());
        }
        return Optional.empty();
    }

    @Override
    public HashMap<Long, Integer> getAllProducts(HashMap<Long, Integer> products) {
        HashMap<Long, Integer> productsList = new HashMap<>();

        Set<Long> keys = products.keySet();
        keys.forEach(k -> {
            if (products.containsKey(k)) {
                productsList.put(k, productStock.get(k).getQuantity());
            }
        });
        return productsList;
    }

    @Override
    public void checkoutCart(HashMap<Long, Integer> products) {
        products.forEach((k,v) -> {
            if (productStock.containsKey(k)) {
                ProductEntity productEntity = productStock.get(k);
                Integer actualQuantity = productEntity.getQuantity() - v;
                productStock.replace(k,new ProductEntity(k,actualQuantity, productEntity.getUnitPrice()));
            }
        });
    }
}
