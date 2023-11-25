package com.redisdemo.redisexample.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.redisdemo.redisexample.entity.Student;
import com.redisdemo.redisexample.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@EnableCaching // either declared here or declared main entry point class

public class StudentController {
/* @Cacheable: Used to indicate that the result of a method should be cached.
@CachePut: Used to update the cache with the result of a method (also invokes the method).
@CacheEvict: Used to remove items from the cache.
 */

    @Autowired
    private StudentRepository studentRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/savestudent")
    public Student saveStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/getstudents")
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Cacheable(value = "mycache", key = "#id") // for retriving into cache
    @GetMapping("/getstudent/{id}")
    public Student getStudent(@PathVariable int id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        log.info("repository");
        if (studentOptional.isPresent()) {

            return studentOptional.get();
        }

        return null;
    }

    @CacheEvict(value = "mycache", key = "#id") // delete object into cache
    @DeleteMapping("/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentRepository.deleteById(id);
        return "deleted sucessfully";

    }

    @CachePut(value = "mycache", key = "#id") // when object is added to database add in cache also
    @PutMapping("/updatestudent/{id}")
    public String updateStudent(@PathVariable int id, @RequestBody Student student) {
        // if there is object present with id then it just updated that object otherwise
        // it create new object
        student.setId(id);
        studentRepository.save(student);
        return "update sucessfully";
    }

}
