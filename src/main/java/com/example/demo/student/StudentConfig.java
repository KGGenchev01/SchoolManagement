package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student Mariyan = new Student(
                    "Mariyan",
                    "mariyang@gmail.com",
                    LocalDate.of(2001, Month.JANUARY,7)
            );

            Student Alex = new Student(
                    "Alex",
                    "alexg@gmail.com",
                    LocalDate.of(1999, Month.JANUARY,7)
            );

            repository.saveAll(
                    List.of(Mariyan, Alex)
            );
        };
    }
}
