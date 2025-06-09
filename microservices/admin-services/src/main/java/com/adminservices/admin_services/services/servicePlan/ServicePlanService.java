package com.adminservices.admin_services.services.servicePlan;

import com.adminservices.admin_services.dto.ServicePlanDTO;
import com.adminservices.admin_services.entity.ServicePlanEntity;
import com.adminservices.admin_services.exceptionhandling.NotFoundException;
import com.adminservices.admin_services.repository.ServicePlanRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServicePlanService {

    @Autowired
    private ServicePlanRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public String generateServiceId(){
        return "SERVICEID_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public ServicePlanDTO addOrUpdateServicePlan(ServicePlanDTO dto) {
        dto.setServiceId(generateServiceId());
        ServicePlanEntity entity = modelMapper.map(dto, ServicePlanEntity.class);
        ServicePlanEntity saved = repository.save(entity);
        return modelMapper.map(saved, ServicePlanDTO.class);
    }

    public List<ServicePlanDTO> getAllPlans() {
        return repository.findAll().stream()
                .map(plan -> modelMapper.map(plan, ServicePlanDTO.class))
                .collect(Collectors.toList());
    }

    public void togglePlanStatus(String id) {
        ServicePlanEntity plan = repository.findByServiceId(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        plan.setActiveStatus(!plan.getActiveStatus());
        repository.save(plan);
    }

    public List<ServicePlanDTO> getAllPlanByName(String name){
        return repository.findAllByName(name).stream()
                .map(plan -> modelMapper.map(plan,ServicePlanDTO.class))
                .collect(Collectors.toList());
    }
}

