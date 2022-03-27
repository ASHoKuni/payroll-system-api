package com.payroll.payments.repositroys;

import java.util.Date;
import java.util.Optional;

import com.payroll.payments.dto.Payments;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "payments", path = "payments")
public interface PaymentsRepository extends MongoRepository<Payments, Long> {

    Optional<Payments> findByPaymentMonth(String monthDate);

}