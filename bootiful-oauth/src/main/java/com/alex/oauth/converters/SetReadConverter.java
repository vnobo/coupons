package com.alex.oauth.converters;

import com.alex.oauth.annotation.RestServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

/**
 * com.alex.web.converters.SetReadConverter
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/12/28
 */
@Log4j2
@ReadingConverter
@RequiredArgsConstructor
public class SetReadConverter implements Converter<Json, Set<Object>> {

  private final ObjectMapper objectMapper;

  @Override
  public Set<Object> convert(@NonNull Json source) {
    if (ObjectUtils.isEmpty(source)) {
      return Set.of();
    }
    try {
      return this.objectMapper.readValue(source.asString(), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error("读取 Json 为 Set 转换错误,信息: {}", e.getMessage());
      throw RestServerException
          .withMsg(HttpStatus.SERVICE_UNAVAILABLE, "序列化数据Json为Set类型错误,信息: " + e.getMessage());
    }
  }
}