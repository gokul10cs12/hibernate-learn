package com.example.hibernatelearn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HibernateLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateLearnApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student gokul =new Student(
                    "Gokul",
                    "N.B",
                    "abc@abc.com",
                    27
            );
            studentRepository.save(gokul);
        };
    }

}
