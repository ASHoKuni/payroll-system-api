package com.payroll.department.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.payroll.department.dto.Department;
import com.payroll.department.repository.DepartmentRepository;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.service.SequenceService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SequenceService seqService;

    @ApiOperation(value = "getAllDepartment", notes = "Get All Department data", nickname = "all")
    @GetMapping("/api/getAllDepartment")
    public List<Department> getAll(){
        
        return departmentRepository.findAll();
    }


    @ApiOperation(value = "createDepartment", notes = "Create a Department record and save to db", nickname = "createDepartment")
    @PostMapping("/api/createDepartment")
    public ResponseMessage createDepartment(@RequestBody Department department){
        department.setId(seqService.getSeqNumber("department"));
    	Department result = departmentRepository.insert(department);
		return new ResponseMessage("Department created succesfully",HttpStatus.OK,result);
    

    }

    @ApiOperation(value = "deleteDepartment", notes = "Delete a Department record ", nickname = "deleteDepartment")
    @PostMapping("/api/deleteDepartment")
    public ResponseMessage deleteEmployee(@RequestParam Long id) {
      Optional<Department> departmentData = departmentRepository.findById(id);
        if(departmentData.isPresent()){
        	departmentRepository.deleteById (id);
          return new ResponseMessage("Department deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Department not found",HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "updateDepartment", notes = "Edit Department record", nickname = "updateDepartment")
    @PostMapping("/api/updateDepartment")
    public ResponseMessage updateEmployee(@RequestBody Department department,@RequestParam Long id) {
        Optional<Department> departmentdata = departmentRepository.findById(id);
        if(departmentdata.isPresent()){
        	department.setId(id);
        	departmentRepository.save(department);
            return new ResponseMessage("Department record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Department not found",HttpStatus.FOUND);
      }

    
}
