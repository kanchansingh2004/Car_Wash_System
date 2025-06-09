package com.customer_service.customerservices.services.customer;

import com.customer_service.customerservices.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDTO> getAllCustomer();
    CustomerDTO addCustomer(String email, String profileImage);
    CustomerDTO updateCustomer(String id, CustomerDTO userDTO);
    void deleteCustomer(String id);
    Optional<CustomerDTO> getCustomerById(String id);
}
