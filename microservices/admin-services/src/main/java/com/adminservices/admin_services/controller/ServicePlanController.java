package com.adminservices.admin_services.controller;

import com.adminservices.admin_services.dto.ServicePlanDTO;
import com.adminservices.admin_services.services.servicePlan.ServicePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request/admin/service-plan")
public class ServicePlanController {

    @Autowired
    private ServicePlanService service;

    @GetMapping("/random")
    public String random(){
        return "hello";
    }
    @PostMapping("/add")
    public ResponseEntity<ServicePlanDTO> addOrUpdate(@RequestBody ServicePlanDTO dto) {
        return ResponseEntity.ok(service.addOrUpdateServicePlan(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServicePlanDTO>> getAll() {
        return ResponseEntity.ok(service.getAllPlans());
    }

    @GetMapping("/plan")
    public ResponseEntity<List<ServicePlanDTO>> getServicesByName(@RequestParam String name){
        return ResponseEntity.ok(service.getAllPlanByName(name));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<String> toggleStatus(@PathVariable String id) {
        service.togglePlanStatus(id);
        return ResponseEntity.ok("Status updated successfully.");
    }
}

