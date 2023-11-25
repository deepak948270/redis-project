package com.redisdemo.redisexample.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("student")
public class Student implements Serializable {

    @Id
    private int id ;
    private String name;
    private String course;
    
}
