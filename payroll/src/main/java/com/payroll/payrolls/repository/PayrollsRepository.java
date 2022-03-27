package com.payroll.payrolls.repository;

import com.payroll.payrolls.dto.Payrolls;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "payrolls", path = "payrolls")
public interface PayrollsRepository extends MongoRepository<Payrolls, Long> {

}