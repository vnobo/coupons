package com.coupon.oauth.converters;

import com.coupon.oauth.annotation.RestServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * com.mspbots.web.security.converter.UserWriteConverter
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/17
 */
@WritingConverter
@Log4j2
@RequiredArgsConstructor
public class SetWriteConverter implements Converter<Set<Object>, Json> {

    private final ObjectMapper objectMapper;

    @Override
    public Json convert(@NonNull Set<Object> source) {
        if (ObjectUtils.isEmpty(source)) {
            return Json.of("[]");
        }
        try {
            return Json.of(this.objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            log.error("写入 Set 为 Json 转换错误,信息: {}", e.getMessage());
            throw RestServerException
                    .withMsg(HttpStatus.SERVICE_UNAVAILABLE, "序列化数据Set为Json类型错误,信息: " + e.getMessage());
        }
    }
}