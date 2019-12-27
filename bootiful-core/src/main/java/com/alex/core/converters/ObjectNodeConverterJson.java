package com.alex.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Converter
public class ObjectNodeConverterJson implements AttributeConverter<ObjectNode, String> {

    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(ObjectNode attribute) {

        if (ObjectUtils.isEmpty(attribute)) {
            return "{}";
        }

        try {
            return this.objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new HibernateException("ObjectNodeConverterJson unable to read object from result set", e);
        }
    }

    @Override
    public ObjectNode convertToEntityAttribute(String dbData) {

        if (StringUtils.isEmpty(dbData)) {
            return this.objectMapper.createObjectNode();
        }

        try {
            return this.objectMapper.readTree(dbData).deepCopy();
        } catch (IOException e) {
            throw new HibernateException("ObjectNodeConverterJson unable to read object from result get", e);
        }
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
