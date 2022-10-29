package ar.lamansys.cart.infrastructure.input.rest.cart.adapter;

import ar.lamansys.cart.domain.Cart.CartBO;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.response.CartDTOResponse;
import ar.lamansys.cart.infrastructure.input.rest.cart.dto.response.CartDTOResponseWithProcessed;
import ar.lamansys.cart.infrastructure.output.repository.cart.database.entity.CartJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CartAdapter {

    @Mapping(source = "productBOList",target = "products")
    @Mapping(source = "userId",target = "userId")
    public abstract CartDTOResponse toCartDTOResponse(CartBO cartBO);

    @Mapping(source = "isProcessed",target = "processed")
    @Mapping(source = "productBOList",target = "products")
    @Mapping(source = "userId",target = "userId")
    public abstract CartDTOResponseWithProcessed toCartDTOResponseWithProcessed(CartBO cartBO);

    @Mapping(source = "isProcessed",target = "processed")
    @Mapping(source = "userId",target = "user_id")
    public abstract CartJPAEntity toCartJPAEntity(CartBO cartBO);

    @Mapping(source = "processed",target = "isProcessed")
    @Mapping(source = "user_id",target = "userId")
    public abstract CartBO toCartBO(CartJPAEntity cartJPAEntity);


}

