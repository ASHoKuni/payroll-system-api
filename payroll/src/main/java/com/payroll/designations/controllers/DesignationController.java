package com.payroll.designations.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

import com.payroll.designations.dto.Designation;
import com.payroll.designations.repository.DesignationRepository;
import com.payroll.login.dto.ResponseMessage;
import com.payroll.login.service.SequenceService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class DesignationController {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private SequenceService seqService;

    @ApiOperation(value = "getAllDesignation", notes = "Get All Designation data", nickname = "all")
    @GetMapping("/api/getAllDesignation")
    public List<Designation> getAll(){
        
        return designationRepository.findAll();
    }


    @ApiOperation(value = "createDesignation", notes = "Create a Designation record and save to db", nickname = "createDesignation")
    @PostMapping("/api/createDesignation")
    public ResponseMessage createDesignation(@RequestBody Designation designation){
        designation.setId(seqService.getSeqNumber("designation"));
    	Designation result = designationRepository.insert(designation);
		return new ResponseMessage("Designation created succesfully",HttpStatus.OK,result);
    

    }

    @ApiOperation(value = "deleteDesignation", notes = "Delete a Designation record ", nickname = "deleteDesignation")
    @PostMapping("/api/deleteDesignation")
    public ResponseMessage deleteDesignation(@RequestParam Long id) {
      Optional<Designation> designationData = designationRepository.findById(id);
        if(designationData.isPresent()){
        	designationRepository.deleteById (id);
          return new ResponseMessage("Designation deleted succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Designation not found",HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "updateDesignation", notes = "Edit Designation record", nickname = "updateDesignation")
    @PostMapping("/api/updateDesignation")
    public ResponseMessage updateDesignation(@RequestBody Designation designation,@RequestParam Long id) {
        Optional<Designation> designationdata = designationRepository.findById(id);
        if(designationdata.isPresent()){
        	designation.setId(id);
        	designationRepository.save(designation);
            return new ResponseMessage("Designation record updated succesfully",HttpStatus.OK);
        }
        return new ResponseMessage("Designation not found",HttpStatus.FOUND);
      }

    
}
