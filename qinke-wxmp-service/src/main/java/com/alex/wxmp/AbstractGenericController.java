package com.alex.wxmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;

public abstract class AbstractGenericController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected ObjectMapper objectMapper;

    protected ProjectionFactory factory;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setFactory(ProjectionFactory factory) {
        logger.debug("Abstract class generic controller set factory");
        this.factory = factory;
    }

    protected class RequestException extends RestServerException{

        public RequestException(int code, String msg) {
            super(code, msg);
        }
    }
}
