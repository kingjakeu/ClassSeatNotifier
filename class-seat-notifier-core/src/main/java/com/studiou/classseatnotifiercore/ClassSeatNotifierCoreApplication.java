package com.studiou.classseatnotifiercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class ClassSeatNotifierCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassSeatNotifierCoreApplication.class, args);
    }

}
