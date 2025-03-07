package co.edu.escuelaing.edu.arep.secure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping
    public String hello(){
        return "Hello Welcome To SpringBoot";
    }
}
