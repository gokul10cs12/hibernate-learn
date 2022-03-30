package com.example.hibernatelearn.controller;

import com.example.hibernatelearn.domain.College;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/control")
public class MyController {

    @PostMapping("/postMe")
    List<College> createStudent(@RequestBody List<College> college){
        return college;
    }

    @GetMapping("/test")
    String testApi(){
        return "working";
    }

}
