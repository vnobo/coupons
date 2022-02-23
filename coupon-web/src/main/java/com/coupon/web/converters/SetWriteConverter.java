package com.coupon.web.converters;

import cn.hutool.json.JSONUtil;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.util.ObjectUtils;

import java.util.Set;

/**
 * com.mspbots.web.security.converter.UserWriteConverter
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/17
 */
@WritingConverter
public class SetWriteConverter implements Converter<Set<Object>, Json> {

    @Override
    public Json convert(Set<Object> source) {
        if (ObjectUtils.isEmpty(source)) {
            return Json.of("[]");
        }
        return Json.of(JSONUtil.toJsonStr(source));
    }
}