package com.payroll.employee.dto;

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
@Document(collection = "Employee")
public class Employee {

    @Transient
    public static final String SEQUENCE_NAME="employeeId";
    @Id
    private java.lang.Long id;
    private java.lang.Long empid;
    private String empFirstName;
    private String empMiddleName;
    private String empLastName;
    private String empMontherName;
    private String empEmail;
    private String empContactNo1;
    private String empCountryCode1;
    private String empContactNo2;
    private String empCountryCode2;
    private String empGender;
    private String empJoingDate;
    private String empPresentAddress;
    private String empPermentAddress;
    private String empIdName;
    private String empIdnumber;
    private String empDesignation;
    private String empDepartment;
    private String empMaritalStatus;
    private String empDateofBirth;
    private String empPFUANno;
    private String empBankAccountNumber;

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
