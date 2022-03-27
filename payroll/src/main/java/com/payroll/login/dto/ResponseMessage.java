package com.payroll.login.dto;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseMessage {

    private String message;
    private HttpStatus found;
    private Object data;

    public ResponseMessage(String message, HttpStatus found) {
        this.message = message;
        this.found = found;

    }

    public ResponseMessage(String message, HttpStatus found, Object data) {
        this.message = message;
        this.found = found;
        this.data = data;
    }

}
