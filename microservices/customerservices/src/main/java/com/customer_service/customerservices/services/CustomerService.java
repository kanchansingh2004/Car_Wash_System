package com.customer_service.customerservices.services;

import com.customer_service.customerservices.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getAllCustomer();
    CustomerDTO createCustomer(CustomerDTO userDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO userDTO);
    void deleteCustomer(Long id);
    Optional<CustomerDTO> getCustomerById(Long id);
}
