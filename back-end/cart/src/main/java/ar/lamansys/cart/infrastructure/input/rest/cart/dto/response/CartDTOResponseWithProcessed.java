package ar.lamansys.cart.infrastructure.input.rest.cart.dto.response;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTOResponseWithProcessed {
    private Long userId;
    private HashMap<Long,Integer> products;
    private Boolean processed;
}
