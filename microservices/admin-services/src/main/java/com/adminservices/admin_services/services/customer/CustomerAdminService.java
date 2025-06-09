package com.adminservices.admin_services.services.customer;

import com.adminservices.admin_services.dto.CustomerDTO;
import com.adminservices.admin_services.entity.AdminCustomerEntity;
import com.adminservices.admin_services.entity.AdminCustomerEntity;
import com.adminservices.admin_services.exceptionhandling.NotFoundException;
import com.adminservices.admin_services.repository.AdminCustomerRepository;
import com.adminservices.admin_services.util.CustomerServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerAdminService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerAdminService.class);

    @Autowired
    private CustomerServiceClient customerServiceClient;

    @Autowired
    private AdminCustomerRepository adminCustomerRepository;

    public List<Map<String, Object>> getAllCustomers() {
        logger.info("Fetching all customers from Customer Service.");
        List<Map<String, Object>> responseList = new ArrayList<>();
        List<CustomerDTO> customerDTOList = customerServiceClient.getAllCustomers();

        for (CustomerDTO customer : customerDTOList) {
            Optional<AdminCustomerEntity> adminCustomer = adminCustomerRepository.findByCustomerId(customer.getUserId());
            Map<String, Object> customerData = new HashMap<>();
            customerData.put("customerDetails", customer);
            customerData.put("activeStatus", adminCustomer.map(AdminCustomerEntity::getActiveStatus).orElse(null));
            responseList.add(customerData);
        }

        logger.info("Fetched and prepared {} customer records.", responseList.size());
        return responseList;
    }

    public Map<String, Object> getCustomerById(String id) {
        logger.info("Fetching customer by ID: {}", id);
        CustomerDTO customer = customerServiceClient.getCustomerById(id);

        AdminCustomerEntity adminCustomer = adminCustomerRepository.findByCustomerId(id)
                .orElseThrow(() -> {
                    logger.error("Customer not found in Admin DB with ID: {}", id);
                    return new RuntimeException("Customer not found in Admin database");
                });

        Map<String, Object> response = new HashMap<>();
        response.put("customerDetails", customer);
        response.put("activeStatus", adminCustomer.getActiveStatus());

        logger.info("Customer fetched successfully: {}", response);
        return response;
    }

    public List<AdminCustomerEntity> addCustomerFromCustomerService() {
        logger.info("Adding all customers from Customer Service into Admin DB.");
        List<AdminCustomerEntity> list = new ArrayList<>();
        List<CustomerDTO> customerDTOList = customerServiceClient.getAllCustomers();

        for (CustomerDTO customer : customerDTOList) {
            AdminCustomerEntity entity = new AdminCustomerEntity();
            entity.setCustomerId(customer.getUserId());
            entity.setActiveStatus(true);
            adminCustomerRepository.save(entity);
            list.add(entity);
        }

        logger.info("Successfully added {} customers to Admin DB.", list.size());
        return list;
    }

    public AdminCustomerEntity updateStatus(String customerId, Boolean activeStatus) {
        AdminCustomerEntity entity = adminCustomerRepository.findByCustomerId(customerId).orElseThrow(() -> {
            logger.error("Customer not found with ID: {}", customerId);
            return new NotFoundException("Customer not found with ID: " + customerId);
        });
        entity.setActiveStatus(activeStatus);
        adminCustomerRepository.save(entity);
        return entity;
    }
}
