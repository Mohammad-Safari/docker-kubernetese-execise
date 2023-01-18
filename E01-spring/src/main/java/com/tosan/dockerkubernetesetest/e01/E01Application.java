package com.tosan.dockerkubernetesetest.e01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class E01Application {

    public static void main(String[] args) {
        SpringApplication.run(E01Application.class, args);
    }

    @GetMapping(value = "hello")
    public String sayHello(@RequestParam(value = "name", required = false) String name) {
        return "Hello " + (name != null ? name : "Stranger");
    }

    @GetMapping(value = "author")
    public String getAuthor() {
        return "MohammadSafari";
    }

}
