package ar.lamansys.cart.infrastructure.input.rest.cart.adapter;

import ar.lamansys.cart.domain.Product.ProductBO;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.request.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductAdapter {

    @Mapping(source = "id", target="id")
    ProductBO toProductBO(ProductDTO productDTO);

    @Mapping(source = "id",target = "id")
    ProductDTO toProductDTO(ProductBO productBO);
}
