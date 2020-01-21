package com.alex.wx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseGenericController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected class RequestException extends RestServerException{

        public RequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
