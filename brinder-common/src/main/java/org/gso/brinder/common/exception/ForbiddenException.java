package org.gso.brinder.common.exception;

import org.gso.brinder.common.dto.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractBrinderException {
    public static final ForbiddenException DEFAULT = new ForbiddenException();

    public static final String FORBIDDEN_CODE = "err.func.brinder.forbidden";
    public static final String FORBIDDEN_MESSAGE = "The access is forbidden";

    private ForbiddenException() {
        super(HttpStatus.FORBIDDEN,
                ErrorMessage.builder()
                        .code(FORBIDDEN_CODE)
                        .message(FORBIDDEN_MESSAGE)
                        .build());
    }
}
