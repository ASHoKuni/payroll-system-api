package com.payroll.increments.controllers;

 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.payroll.employee.dto.Employee;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.increments.dto.Increment;
import com.payroll.increments.repositorys.IncrementRepository;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.service.SequenceService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class IncrementController {

    @Autowired
    private IncrementRepository incrementRepository;

    @Autowired
    private SequenceService seqService;


    @ApiOperation(value = "getAllIncrementEmployee", notes = "Get All Increment Employee data", nickname = "all")
    @GetMapping("/api/getAllIncrementEmployee")
    public List<Increment> getAll(){
        
        return incrementRepository.findAll();
    }

    @ApiOperation(value = "createIncrementEmployee", notes = "Create aIncrementEmployee record and save to db", nickname = "IncrementEmployee")
    @PostMapping("/api/createIncrementEmployee")
    public ResponseMessage createIncrementEmployee(@RequestBody Increment increment,@RequestParam Long empId){
        increment.setId(seqService.getSeqNumber("increment"));
        increment.setEmpId(empId);
        Increment result = incrementRepository.insert(increment);
		return new ResponseMessage("Increment created succesfully",HttpStatus.OK,result);
    }

    @ApiOperation(value = "getIncrementEmployeeById", notes = "get Increment Employee record by id", nickname = "getIncrementEmployeeById")
    @GetMapping("/api/getIncrementEmployeeById")
    public Optional<Increment> getIncrementEmployeeById(@RequestParam Long id) {
      Optional<Increment> incrementdata = incrementRepository.findById(id);
      return incrementdata;
    }

    @ApiOperation(value = "deleteIncrementEmployee", notes = "Delete a Increment Employee record ", nickname = "deleteIncrementEmployee")
    @PostMapping("/api/deleteIncrementEmployee")
    public ResponseMessage deleteIncrementEmployee(@RequestParam Long id) {
      Optional<Increment> incrementdata = incrementRepository.findById(id);
        if(incrementdata.isPresent()){
        	incrementRepository.deleteById (id);
          return new ResponseMessage("Increment Employee deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Increment Employee not found",HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "updateIncrementEmployee", notes = "Edit Increment Employee record", nickname = "updateIncrementEmployee")
    @PostMapping("/api/updateIncrementEmployee")
    public ResponseMessage updateIncrementEmployee(@RequestBody Increment increment,@RequestParam Long id) {
        Optional<Increment> incrementdata = incrementRepository.findById(id);
        if(incrementdata.isPresent()){
        	increment.setId(id);
        	incrementRepository.save(increment);
            return new ResponseMessage("Increment Employee record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Increment Employee not found",HttpStatus.FOUND);
      }

    
}
