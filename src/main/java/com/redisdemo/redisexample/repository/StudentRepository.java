package com.redisdemo.redisexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redisdemo.redisexample.entity.Student;


@Repository
public interface StudentRepository extends CrudRepository<Student,Integer>{
    
    
}
