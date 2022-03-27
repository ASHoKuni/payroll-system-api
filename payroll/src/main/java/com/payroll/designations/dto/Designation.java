package com.payroll.designations.dto;

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
@Document(collection = "Designation")
public class Designation {

    @Transient
    public static final String SEQUENCE_NAME="designationId";
    @Id
    private java.lang.Long id;
    private String designationName;
    private String departmentId;
    private String designationStatus;
    private String designationDesc;

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
