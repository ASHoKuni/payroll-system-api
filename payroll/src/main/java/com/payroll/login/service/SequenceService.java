package com.payroll.login.service;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Objects;

import com.payroll.login.dto.Sequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.stereotype.Service;


@Service
public class SequenceService {

    @Autowired
    public MongoOperations mongoOperations;

    public java.lang.Long getSeqNumber(String modelName){
        System.out.println(modelName);
        Query query =new Query(Criteria.where("modelname").is(modelName));
        Update update =new Update().inc("seq",1);
        Sequence counter = mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),Sequence.class);

        return java.lang.Long.valueOf( !Objects.isNull(counter)?counter.getSeq():1);
    }

    public java.lang.Long getSeqNumberWithoutInc(String modelName){
        System.out.println(modelName);
        Query query =new Query(Criteria.where("modelname").is(modelName));
        Update update =new Update().inc("seq",0);
        Sequence counter = mongoOperations.findAndModify(query,update,options().returnNew(true).upsert(true),Sequence.class);

        return java.lang.Long.valueOf( !Objects.isNull(counter)?counter.getSeq():1);
    }
}