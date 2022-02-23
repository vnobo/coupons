package com.coupon.oauth.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * mspbots.data.cw.core.annotation.AutoTaskApiException
 *
 * @author <a href="https://github.com/vnobo">Alex bob</a>
 * @date Created by 2020/7/22
 */
@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class RestServerException extends RuntimeException implements Serializable {

    private Object msg;
    private HttpStatus status;

    public RestServerException(HttpStatus status, Object msg) throws JsonProcessingException {
        super(new ObjectMapper().writeValueAsString(msg));
        this.msg = msg;
        this.status = status;
    }

    public static RestServerException withMsg(HttpStatus status, Object msg) {
        try {
            return new RestServerException(status, msg);
        } catch (JsonProcessingException e) {
            log.error("Exception 序列化消息错误,信息: {}", e.getMessage());
        }
        return RestServerException.withMsg(status, msg.toString());
    }
}