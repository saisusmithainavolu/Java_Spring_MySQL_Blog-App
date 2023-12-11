package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionHandler extends RuntimeException{

    private String resourceName;
    private String FieldName;
    private long FieldValue;


    public ResourceNotFoundExceptionHandler(String resourceName, String fieldName, long fieldValue) {
        // sending a custom message to super class that resource is not found
        // eg: post not found with id : 2

        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        FieldName = fieldName;
        FieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return FieldName;
    }

    public long getFieldValue() {
        return FieldValue;
    }
}
