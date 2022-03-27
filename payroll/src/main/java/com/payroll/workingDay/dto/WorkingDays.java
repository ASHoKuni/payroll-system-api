package com.payroll.workingDay.dto;

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
@Document(collection = "WorkingDays")
public class WorkingDays {

    @Transient
    public static final String SEQUENCE_NAME="workingdayId";
    @Id
    private java.lang.Long id;
    private java.lang.Long empId;
    private String workingDay;
    private String leavesDay;
    private String workingdate;

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

