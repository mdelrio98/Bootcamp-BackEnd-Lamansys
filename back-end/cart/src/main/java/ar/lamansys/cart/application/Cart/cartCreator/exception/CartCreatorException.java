package ar.lamansys.cart.application.Cart.cartCreator.exception;

import lombok.Getter;

public class CartCreatorException extends RuntimeException{

    @Getter
    private CodeCartCreatorException code;

    public CartCreatorException(CodeCartCreatorException code, String message) {
        super(message);
        this.code = code;
    }

}
