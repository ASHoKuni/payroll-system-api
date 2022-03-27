package com.payroll.master.repository;

import com.payroll.master.dto.Master;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MasterRepository extends MongoRepository<Master,Long> {
}
