package com.payroll.designations.repository;

import com.payroll.designations.dto.Designation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "designation", path = "designation")
public interface DesignationRepository extends MongoRepository<Designation, Long> {

}