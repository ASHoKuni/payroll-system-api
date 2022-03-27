package com.payroll.login.repository;

import com.payroll.login.dto.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLoginRepository extends MongoRepository<User,Long> {

    User findByEmail(String emailId);
    User findByPasswordResetToken(String token);
    
}
