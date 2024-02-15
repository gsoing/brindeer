package org.gso.brinder.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum ErrorMessageType {
    TECHNICAL("err.tech.", HttpStatus.INTERNAL_SERVER_ERROR),
    FUNCTIONAL("err.func.", HttpStatus.BAD_REQUEST);

    private final String prefix;
    private final HttpStatus defaultStatus;

    public static ErrorMessageType fromStatus(HttpStatus status) {
        if (status.is4xxClientError()) {
            return FUNCTIONAL;
        } else if (status.is5xxServerError()) {
            return TECHNICAL;
        }
        throw new IllegalArgumentException("HTTP status '" + status + "' is not a valid exception status.");
    }
}
