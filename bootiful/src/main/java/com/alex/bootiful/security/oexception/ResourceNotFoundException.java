package com.alex.bootiful.security.oexception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * qinke-coupons com.alex.bootiful.security.oexception.ResourceNotFoundException
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/4/25
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
