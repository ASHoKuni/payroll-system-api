package com.payroll.login.repository;

import com.payroll.login.dto.Sequence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sequences", path = "sequences")
public interface SequenceRepository extends MongoRepository<Sequence, String> {

}