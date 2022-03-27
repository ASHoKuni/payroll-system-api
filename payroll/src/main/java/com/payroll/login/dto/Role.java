package com.payroll.login.dto;

import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "Role")
public class Role {
    @Id
    private java.lang.Long id;
    private String roleName;
    private String roleDescription;
    private String createdBy;
    private String createdDateTime;
    private String modifiedBy;
    private String modifiedDateTime;
}