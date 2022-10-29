package ar.lamansys.cart.infrastructure.input.rest.cart.dto.response;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTOResponse {
    private Long userId;
    private HashMap<Long,Integer> products;
}
