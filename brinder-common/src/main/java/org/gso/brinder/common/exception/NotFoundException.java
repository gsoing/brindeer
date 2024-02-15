package org.gso.brinder.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.gso.brinder.common.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class NotFoundException extends AbstractBrinderException {
    public static final NotFoundException DEFAULT = new NotFoundException();

    public static final String NOT_FOUND_CODE = "err.func.brinder.notfound";
    public static final String NOT_FOUND_MESSAGE = "The Ressource is not foud";

    private NotFoundException() {
        super(HttpStatus.NOT_FOUND,
                ErrorMessage.builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND_MESSAGE)
                        .build());
    }
}
