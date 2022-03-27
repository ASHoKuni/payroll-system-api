package com.payroll.login.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "User")
public class User {
    @Transient
    public static final String SEQUENCE_NAME="userId";
    @Id
    private java.lang.Long id;
    private long role;
    private String email;
    private String firstName;
    private String middleName;
    private String userName;
    private String lastName;
    private String password;
    private String contactNo;
    private Long countryCode;
    private String status;
    private String note;
    private String profilPicture;

    







    @CreatedBy
    private String createdBy;

    @Indexed
    @CreatedDate
    private Date createdDatetime = new Date();

    @LastModifiedBy
    private String modifiedBy;
    @Indexed
    @LastModifiedDate
    private Date modifiedDatetime = new Date();
    private String passwordResetToken;
    private Date passwordResetTokenDate;
}


