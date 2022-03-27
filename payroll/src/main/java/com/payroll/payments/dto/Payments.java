package com.payroll.payments.dto;

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
@Document(collection = "Payments")
public class Payments {

    @Transient
    public static final String SEQUENCE_NAME="paymentId";
    @Id
    private java.lang.Long id;
    private java.lang.Long employeeId;
    private String employeeType;
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
    private String paymentMonth;
    private String paymentType;
    private String note;


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

