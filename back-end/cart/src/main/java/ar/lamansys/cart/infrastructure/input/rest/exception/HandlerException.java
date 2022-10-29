package ar.lamansys.cart.infrastructure.input.rest.exception;

import ar.lamansys.cart.application.Cart.cartCreator.exception.CartCreatorException;
import ar.lamansys.cart.application.Cart.cartGetter.exception.CartGetterException;
import ar.lamansys.cart.application.Cart.cartProductAdder.CartProductAdder;
import ar.lamansys.cart.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "ar.lamansys.cart.infrastructure.input.rest")
public class HandlerException {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorDTO> getError(DomainException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(e.getMessage(), e.getCode().toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> getError(Exception e) {
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(e.getMessage(), null));
    }

    @ExceptionHandler(CartCreatorException.class)
    public ResponseEntity<ErrorDTO> getError(CartCreatorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(e.getMessage(), e.getCode().toString()));
    }

    @ExceptionHandler(CartGetterException.class)
    public ResponseEntity<ErrorDTO> getError(CartGetterException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(e.getMessage(),e.getCode().toString()));
    }

}
