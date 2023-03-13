package com.juaracoding.mafjavaweb.handler;

import lombok.Data;

@Data
class ApiValidationError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String field, String message,Object rejectedValue,String object) {

        this.object = object;
        this.message = message;
        this.rejectedValue=rejectedValue;
        this.field = field;
    }
}