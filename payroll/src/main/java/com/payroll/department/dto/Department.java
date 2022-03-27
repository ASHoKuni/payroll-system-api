package com.payroll.department.dto;

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
@Document(collection = "Department")
public class Department {

    @Transient
    public static final String SEQUENCE_NAME="departmentId";
    @Id
    private java.lang.Long id;
    private String departmentName;
    private String departmentDesc;
    private String departmentStatus;

    @CreatedBy
    private String createdById;
    @Indexed
    @CreatedDate
    private Date createdOn = new Date();
    @LastModifiedBy
    private String modifiedBy;
    @Indexed
    @LastModifiedDate
    private Date modifiedOn = new Date();
    
    
}
