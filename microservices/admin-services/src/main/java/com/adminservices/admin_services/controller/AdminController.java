package com.adminservices.admin_services.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request/admin")
public class AdminController {
    @GetMapping("random")
    public String testing(){
        return "hello";
    }
}
