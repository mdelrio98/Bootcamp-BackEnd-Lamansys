package ar.lamansys.cart.application.Cart.cartGetter.exception;

import lombok.Getter;

public class CartGetterException extends RuntimeException{

    @Getter
    private CodeCartGetterException code;

    public CartGetterException(CodeCartGetterException code, String message) {
        super(message);
        this.code = code;
    }

}
