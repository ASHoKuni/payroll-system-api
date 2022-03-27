package com.payroll.employee.controllers;

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

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SequenceService seqService;


    @ApiOperation(value = "getAllEmployee", notes = "Get All Employee data", nickname = "all")
    @GetMapping("/api/getAllEmployee")
    public List<Employee> getAll(){
        
        return employeeRepository.findAll();
    }

    @ApiOperation(value = "createEmployee", notes = "Create a Employee record and save to db", nickname = "createEmployee")
    @PostMapping("/api/createEmployee")
    public ResponseMessage createCandidate(@RequestBody Employee employee){
        employee.setId(seqService.getSeqNumber("employee"));
    	Employee result = employeeRepository.insert(employee);
		return new ResponseMessage("Employee created succesfully",HttpStatus.OK,result);
    

    }

    @ApiOperation(value = "getEmployeeById", notes = "get employee record by id", nickname = "getEmployeeById")
    @GetMapping("/api/getEmployeeById")
    public Optional<Employee> getEmployee(@RequestParam Long id) {
      Optional<Employee> employeedata = employeeRepository.findById(id);
      return employeedata;
    }

    @ApiOperation(value = "deleteEmployee", notes = "Delete a Employee record ", nickname = "deleteEmployee")
    @PostMapping("/api/deleteEmployee")
    public ResponseMessage deleteEmployee(@RequestParam Long id) {
      Optional<Employee> employeedata = employeeRepository.findById(id);
        if(employeedata.isPresent()){
        	employeeRepository.deleteById (id);
          return new ResponseMessage("Employee deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Employee not found",HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "updateEmployee", notes = "Edit Employee record", nickname = "updateEmployee")
    @PostMapping("/api/updateEmployee")
    public ResponseMessage updateEmployee(@RequestBody Employee employee,@RequestParam Long id) {
        Optional<Employee> employeedata = employeeRepository.findById(id);
        if(employeedata.isPresent()){
        	employee.setId(id);
        	employeeRepository.save(employee);
            return new ResponseMessage("Employee record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Employee not found",HttpStatus.FOUND);
      }

    
}
