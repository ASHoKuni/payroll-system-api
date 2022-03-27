package com.payroll.increments.repositorys;

import com.payroll.increments.dto.Increment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "increment", path = "increment")
public interface IncrementRepository extends MongoRepository<Increment, Long> {

}
