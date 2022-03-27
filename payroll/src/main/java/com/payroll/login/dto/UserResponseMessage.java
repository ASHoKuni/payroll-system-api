package com.payroll.login.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseMessage {

    private String message;
    private String token;


    public UserResponseMessage(String message, String token) {
        this.message = message;
        this.token = token;

    }

}
