package com.jhooq.demo.Jhooqdockerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JhooqDockerDemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Docker Demo - Guten Appetit persoenlich uebermittelt von Marius :)";
    }
}
