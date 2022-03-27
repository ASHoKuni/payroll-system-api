package com.payroll.master.controller;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.payroll.master.dto.Master;
import com.payroll.master.repository.MasterRepository;

@RestController
public class MasterController {

    @Autowired
    private MasterRepository masterRepository;

    @ApiOperation(value = "getMasterData", notes = "Get All Master Data", nickname = "getMasterData")
    @GetMapping("/api/getMasterData")
    public List<Master> getMasterData(){
        return masterRepository.findAll();
    }
}
