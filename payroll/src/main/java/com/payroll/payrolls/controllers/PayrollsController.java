package com.payroll.payrolls.controllers;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.payroll.employee.dto.Employee;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.service.SequenceService;
import com.payroll.payrolls.dto.Payrolls;
import com.payroll.payrolls.repository.PayrollsRepository;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class PayrollsController {

    @Autowired
    private PayrollsRepository payrollsRepository;

    @Autowired
    private SequenceService seqService;


    @ApiOperation(value = "getAllPayrollsEmployee", notes = "Get All Payrolls Employee data", nickname = "all")
    @GetMapping("/api/getAllPayrollsEmployee")
    public List<Payrolls> getAll(){
        
        return payrollsRepository.findAll();
    }

    @ApiOperation(value = "createPayrollsEmployee", notes = "Create a Payrolls Employee record and save to db", nickname = "PayrollsEmployee")
    @PostMapping("/api/createPayrollsEmployee")
    public ResponseMessage createPayrollsEmployee(@RequestBody Payrolls payrolls,@RequestParam Long empId){
        Optional<Payrolls> payrollsdata = payrollsRepository.findById(empId);

        if(!payrollsdata.isPresent()){
            payrolls.setId(seqService.getSeqNumber("payrolls"));
            payrolls.setEmployeeId(empId);
            Payrolls result = payrollsRepository.insert(payrolls);
		return new ResponseMessage("Payrolls created succesfully",HttpStatus.OK,result);
        }
        return new ResponseMessage("Payrolls already present",HttpStatus.FOUND);
    }

    @ApiOperation(value = "getPayrollsEmployeeById", notes = "get PayrollsEmployee record by id", nickname = "getPayrollsEmployeeById")
    @GetMapping("/api/getPayrollsEmployeeById")
    public Optional<Payrolls> getPayrollsEmployeeById(@RequestParam Long id) {
      Optional<Payrolls> payrollsdata = payrollsRepository.findById(id);
      return payrollsdata;
    }

    @ApiOperation(value = "deletePayrollsEmployee", notes = "Delete a PayrollsEmployee record ", nickname = "deletePayrollsEmployee")
    @PostMapping("/api/deletePayrollsEmployee")
    public ResponseMessage deletePayrollsEmployee(@RequestParam Long id) {
      Optional<Payrolls> payrollsdata = payrollsRepository.findById(id);
        if(payrollsdata.isPresent()){
        	payrollsRepository.deleteById (id);
          return new ResponseMessage("Payrolls Employee deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Payrolls Employee not found",HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "updatePayrollsEmployee", notes = "Edit Payrolls Employee record", nickname = "updatePayrollsEmployee")
    @PostMapping("/api/updatePayrollsEmployee")
    public ResponseMessage updatePayrollsEmployee(@RequestBody Payrolls payrolls,@RequestParam Long id) {
        Optional<Payrolls> payrollsdata = payrollsRepository.findById(id);
        if(payrollsdata.isPresent()){
        	payrolls.setId(id);
        	payrollsRepository.save(payrolls);
            return new ResponseMessage("Payrolls Employee record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Payrolls Employee not found",HttpStatus.FOUND);
      }

    
}
