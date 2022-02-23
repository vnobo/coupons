package com.coupon.web.converters;

import cn.hutool.json.JSONUtil;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.alex.web.converters.SetReadConverter
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@ReadingConverter
public class SetReadConverter implements Converter<Json, Set<Object>> {
    @Override
    public Set<Object> convert(Json source) {
        if (ObjectUtils.isEmpty(source)) {
            return Set.of();
        }
        return JSONUtil.parseArray(source.asString()).toList(String.class).parallelStream().collect(Collectors.toSet());
    }
}