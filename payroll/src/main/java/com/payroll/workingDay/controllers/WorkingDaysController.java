package com.payroll.workingDay.controllers;
 
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
import com.payroll.workingDay.dto.WorkingDays;
import com.payroll.workingDay.repositorys.WorkingDaysRepository;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class WorkingDaysController {

    @Autowired
    private WorkingDaysRepository workingDaysRepository;

    @Autowired
    private SequenceService seqService;


    @ApiOperation(value = "getAllEmployeeWorkingday", notes = "Get All Employee working days data", nickname = "all")
    @GetMapping("/api/getAllEmployeeWorkingday")
    public List<WorkingDays> getAll(){
        
        return workingDaysRepository.findAll();
    }

    @ApiOperation(value = "createEmployeeWorkigDays", notes = "Create Employee WorkigDays record and save to db", nickname = "createEmployeeWorkigDays")
    @PostMapping("/api/createEmployeeWorkigDays")
    public ResponseMessage createEmployeeWorkigDays(@RequestBody WorkingDays increment,@RequestParam Long empId){
        increment.setId(seqService.getSeqNumber("workingday"));
        increment.setEmpId(empId);
        WorkingDays result = workingDaysRepository.insert(increment);
		return new ResponseMessage("WorkingDays created succesfully",HttpStatus.OK,result);
    }

    @ApiOperation(value = "getEmployeeWorkigDaysById", notes = "get EmployeeWorkig Days  record by id", nickname = "getEmployeeWorkigDaysById")
    @GetMapping("/api/getEmployeeWorkigDaysById")
    public Optional<WorkingDays> getEmployeeWorkigDaysById(@RequestParam Long id) {
      Optional<WorkingDays> incrementdata = workingDaysRepository.findById(id);
      return incrementdata;
    }

    @ApiOperation(value = "deleteEmployeeWorkigDays", notes = "Delete a Employee WorkigDays record ", nickname = "deleteEmployeeWorkigDays")
    @PostMapping("/api/deleteEmployeeWorkigDays")
    public ResponseMessage deleteEmployeeWorkigDays(@RequestParam Long id) {
      Optional<WorkingDays> incrementdata = workingDaysRepository.findById(id);
        if(incrementdata.isPresent()){
        	workingDaysRepository.deleteById (id);
          return new ResponseMessage("WorkingDays Employee deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("WorkingDays Employee not found",HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "updateEmployeeWorkigDays", notes = "Edit WorkingDays Employee record", nickname = "updateEmployeeWorkigDays")
    @PostMapping("/api/updateEmployeeWorkigDays")
    public ResponseMessage updateEmployeeWorkigDays(@RequestBody WorkingDays increment,@RequestParam Long id) {
        Optional<WorkingDays> incrementdata = workingDaysRepository.findById(id);
        if(incrementdata.isPresent()){
        	increment.setId(id);
        	workingDaysRepository.save(increment);
            return new ResponseMessage("WorkingDays Employee record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("WorkingDays Employee not found",HttpStatus.FOUND);
      }

    
}
