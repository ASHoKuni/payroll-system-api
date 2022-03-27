package com.payroll.payments.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.payroll.employee.dto.Employee;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.service.SequenceService;
import com.payroll.payments.dto.Payments;
import com.payroll.payments.repositroys.PaymentsRepository;
import com.payroll.payments.util.PdfGenaratorUtil;
import com.payroll.payrolls.dto.Payrolls;
import com.payroll.payrolls.repository.PayrollsRepository;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.MongoTemplate;

@RestController
public class PaymentsController {

  @Autowired
  private PaymentsRepository paymentsRepository;

  @Autowired
  private SequenceService seqService;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  PdfGenaratorUtil pdfGenaratorUtil;

  @ApiOperation(value = "getAllEmployeePayments", notes = "Get All Payments Employee data", nickname = "all")
  @GetMapping("/api/getAllEmployeePayments")
  public List<Payments> getAll() {
    return paymentsRepository.findAll();
  }

  @ApiOperation(value = "createPaymentsEmployee", notes = "Create a Payments Employee record and save to db", nickname = "createPaymentsEmployee")
  @PostMapping("/api/createPaymentsEmployee")
  public ResponseMessage createPaymentsEmployee(@RequestBody Payments payments, @RequestParam Long empId,
      @RequestParam String monthDate) {
    System.out.println("monthDate : " + monthDate);
    Optional<Payments> paymentdata = paymentsRepository.findById(empId);
    Optional<Payments> monthdatedata = paymentsRepository.findByPaymentMonth(monthDate);
    System.out.println("monthdatedata" + monthdatedata);

    // Aggregation stage =
    // newAggregation(match(Criteria.where("paymentMonth").is(monthDate)
    // ));
    // AggregationResults<Payments> statusResult
    // = mongoTemplate.aggregate(stage, Payments.class, Payments.class);
    // List<Payments> statusIndicator=statusResult.getMappedResults();
    // System.out.println("statusIndicator"+statusIndicator);

    if (monthdatedata.isPresent() && paymentdata.isPresent()) {

      return new ResponseMessage("Payments already done", HttpStatus.FOUND);
    } else {
      payments.setId(seqService.getSeqNumber("payments"));
      payments.setEmployeeId(empId);
      payments.setPaymentMonth(monthDate);
      Payments result = paymentsRepository.insert(payments);
      return new ResponseMessage("Payments created succesfully", HttpStatus.OK, result);

    }

  }

  @ApiOperation(value = "getPaymentsEmployeeById", notes = "get Payments Employee  record by id", nickname = "getPaymentsEmployeeById")
  @GetMapping("/api/getPaymentsEmployeeById")
  public Optional<Payments> getPayrollsEmployeeById(@RequestParam Long id) {
    Optional<Payments> paymentssdata = paymentsRepository.findById(id);
    return paymentssdata;
  }

  @ApiOperation(value = "deletePaymentsEmployee", notes = "Delete a PaymentsEmployee record ", nickname = "deletePaymentsEmployee")
  @PostMapping("/api/deletePaymentsEmployee")
  public ResponseMessage deletePaymentsEmployee(@RequestParam Long id) {
    Optional<Payments> payrollsdata = paymentsRepository.findById(id);
    if (payrollsdata.isPresent()) {
      paymentsRepository.deleteById(id);
      return new ResponseMessage("Payments Employee deleted succesfully", HttpStatus.OK);
    }
    return new ResponseMessage("Payments Employee not found", HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "updatePaymentsEmployee", notes = "Edit PaymentsEmployee record", nickname = "updatePaymentsEmployee")
  @PostMapping("/api/updatePaymentsEmployee")
  public ResponseMessage updatePaymentsEmployee(@RequestBody Payments payments, @RequestParam Long id) {
    Optional<Payments> paymentsdata = paymentsRepository.findById(id);
    if (paymentsdata.isPresent()) {
      payments.setId(id);
      paymentsRepository.save(payments);
      return new ResponseMessage("Payments Employee record updated succesfully", HttpStatus.OK);
    }
    return new ResponseMessage("payments Employee not found", HttpStatus.FOUND);
  }

  @ApiOperation(value = "generatePdf", notes = "generatePdf", nickname = "generatePdf")
  @PostMapping("/api/generatePdf")
  public String generatePdf(@RequestBody Payments payments) throws Exception {
    Map<String, Object> data = new HashMap<>();
    data.put("empFirstName",payments.getEmpFirstName());
    data.put("empLastName",payments.getEmpLastName());
    data.put("employeeId", "EMPID"+payments.getId());
    data.put("empJoingDate",payments.getEmpJoingDate());
    data.put("empDesignation",payments.getEmpDesignation());
    data.put("empPFUANno",payments.getEmpPFUANno());
    data.put("paymentMonth",payments.getPaymentMonth());
    data.put("empBankAccountNumber",payments.getEmpBankAccountNumber());
    data.put("basicSalary",payments.getBasicSalary());
    data.put("houseRentAllowance",payments.getHouseRentAllowance());
    data.put("medicalAllowance",payments.getMedicalAllowance());
    data.put("specialAllowance",payments.getEmpBankAccountNumber());
    data.put("taxDeduction",payments.getEmpBankAccountNumber());
    data.put("tds",payments.getEmpBankAccountNumber());
    data.put("telephoneAllowance",payments.getTelephoneAllowance());
    data.put("profDevelopment",payments.getProfDevelopment());
    data.put("totaldeductions",payments.getTotaldeductions());
    data.put("otherDeduction",payments.getOtherDeduction());
    data.put("otherAllowance",payments.getOtherAllowance());
    data.put("netTakeHomePay",payments.getNetTakeHomePay());
    data.put("internetAllowance",payments.getInternetAllowance());
    data.put("fixConveyance",payments.getFixConveyance());
    data.put("employeeProvidentFund",payments.getEmployeeProvidentFund());
    data.put("employerProvidentFund",payments.getEmployerProvidentFund());
    data.put("grossSalary",payments.getGrossSalary());

    pdfGenaratorUtil.createPdf("invoice", data);
    return "PDF generated Successfully";
  }

}
