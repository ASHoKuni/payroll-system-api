package com.payroll.workingDay.repositorys;

import com.payroll.workingDay.dto.WorkingDays;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "workingday", path = "workingday")
public interface WorkingDaysRepository extends MongoRepository<WorkingDays, Long> {

}
