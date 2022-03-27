package com.payroll.increments.dto;

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
@Document(collection = "Incrment")
public class Increment {

    @Transient
    public static final String SEQUENCE_NAME="incrementId";
    @Id
    private java.lang.Long id;
    private String incrementAmount;
    private java.lang.Long empId;
    private String date;
    private String incrPurpose;

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

