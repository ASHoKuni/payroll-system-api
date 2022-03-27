package com.payroll.master.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "Master")
public class Master {
    @Id
    private Long id;
    private String[] salutation;
    private String[] gender;
    private String[] maritalStatus;
    private String[] leaveCategory;
    private String[] typeOfLeave;
    private String[] employmentType;
    private String[] Status;
    private String[] unit_Of_Measure;
    private String[] paymentType;
}
