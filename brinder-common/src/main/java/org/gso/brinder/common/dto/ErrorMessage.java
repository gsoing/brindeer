package org.gso.brinder.common.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage implements Serializable {
    private String code;
    private String message;
}
