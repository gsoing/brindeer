package org.gso.brinder.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractBrinderException extends RuntimeException{
    private final transient ErrorMessage errorMessage;
    private final HttpStatus httpStatus;



    public AbstractBrinderException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public AbstractBrinderException(ErrorMessage errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
