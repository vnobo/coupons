package com.coupon.oauth.annotation;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ErrorResponse
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2020/7/22
 */
@Data
@ApiModel("Request controller error entity model")
public class ErrorResponse implements Serializable {

    private UUID requestId;
    private LocalDateTime time;
    private String message;
    private int status;
    private Object errors;

    @Builder
    public ErrorResponse(@NotEmpty String message, Object errors) {
        this.message = message;
        this.errors = errors;
        this.requestId = UUID.randomUUID();
        this.time = LocalDateTime.now();
    }

    /**
     * default query params
     * max records 25, id>0
     *
     * @return default query body
     */
    public static ErrorResponse withDefault(Object errors) {
        return new ErrorResponse("This request validation failed for argument at params.", errors);
    }

    /**
     * default query params
     * max records 25, id>0
     *
     * @return default query body
     */
    public static ErrorResponse withErrors(String message, Object errors) {
        ErrorResponse response = withDefault(errors);
        response.setMessage(message);
        return response;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }
}