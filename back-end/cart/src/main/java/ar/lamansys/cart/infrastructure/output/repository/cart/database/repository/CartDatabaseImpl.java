package ar.lamansys.cart.infrastructure.output.repository.cart.database.repository;

import ar.lamansys.cart.application.Ports.CartStorage;
import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.output.repository.Mapper.CartMapper;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.CartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.ProductInCartKey;
import ar.lamansys.cart.infrastructure.output.repository.product.database.entity.ProductJPAEntity;
import ar.lamansys.cart.infrastructure.output.repository.product.database.repository.ProductJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@Primary
@AllArgsConstructor
public class CartDatabaseImpl implements CartStorage {

    private CartJPARepository cartJPARepository;

    private ProductInCartRepository productInCartRepository;

    private ProductJPARepository productJPARepository;

    @Override
    public Optional<CartBO> findByUserId(Long id) {
        Optional<CartJPAEntity> optCart = cartJPARepository.findByUserId(id);
        return optCart.map(CartMapper::toCartBO);// Tengo q devolver un cartBO
    }

    @Override
    public Long save(CartBO cart) {
        cartJPARepository.save(new CartJPAEntity(cart.getUserId(), cart.getIsProcessed(),new HashSet<>()));
        CartJPAEntity cartEntity = cartJPARepository.findByUserId(cart.getUserId()).get();
        //storee primero el carrito sin el hashmap
        for(Long i : cart.getProductBOList().keySet()) {
            //storeo el hashmap de id,cantidad por producto en la tabla ProductInCart
            ProductInCartKey key = new ProductInCartKey(i, cartEntity.getUser_id());

            ProductJPAEntity productInStock = productJPARepository.getById(key.getProduct_id());
            ProductInCartJPAEntity productInCart = new ProductInCartJPAEntity(key,productInStock,cartEntity,cart.getProductBOList().get(i));

            productInCartRepository.save(productInCart);
        }
        return cartEntity.getUser_id();
    }

    @Override
    public CartBO removeProduct(Optional<CartBO> cartBO,ProductBO productBO){
        //Ya valide que la cantidad esta disponible
        // actualizo carrito
        cartBO.get().getProductBOList().put(productBO.getId(),cartBO.get().getProductBOList().get(productBO.getId()) - productBO.getQuantity());

        CartBO returnBO =new CartBO(cartBO.get().getUserId(),cartBO.get().getProductBOList(),cartBO.get().getIsProcessed());
        this.save(returnBO);
        return returnBO;
    }

}
