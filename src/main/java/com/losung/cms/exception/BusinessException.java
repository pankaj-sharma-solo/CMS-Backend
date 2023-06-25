package com.losung.cms.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"stackTrace", "suppressed"})
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7235504723801655072L;

    private String errorCode;
    private HttpStatus statusCode;
    private String message;
    private Long timestamp;
}
