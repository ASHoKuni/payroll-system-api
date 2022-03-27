package com.payroll.payrolls.dto;

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
@Document(collection = "Payrolls")
public class Payrolls {

    @Transient
    public static final String SEQUENCE_NAME="payrollsId";
    @Id
    private java.lang.Long id;
    private java.lang.Long employeeId;
    private String employeeType;
    private String basicSalary;
    private String houseRentAllowance;
    private String fixConveyance;
    private String medicalAllowance;
    private String internetAllowance;
    private String specialAllowance;
    private String profDevelopment;
    private String telephoneAllowance;
    private String otherAllowance;
    private String taxDeduction;
    private String otherDeduction;
    private String tds;
    private String employerProvidentFund;
    private String employeeProvidentFund;
    private String grossSalary;
    private String totaldeductions;
    private String netTakeHomePay; 
    private String activationStatus;
   
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


