package com.hackthon.blog_generator_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Blog Generator Backend is running successfully!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
