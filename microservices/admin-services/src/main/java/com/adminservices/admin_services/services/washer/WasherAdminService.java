package com.adminservices.admin_services.services.washer;

import com.adminservices.admin_services.dto.WasherDTO;
import com.adminservices.admin_services.entity.AdminCustomerEntity;
import com.adminservices.admin_services.entity.AdminWasherEntity;
import com.adminservices.admin_services.exceptionhandling.NotFoundException;
import com.adminservices.admin_services.repository.AdminWasherRepository;
import com.adminservices.admin_services.util.WasherServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WasherAdminService {
    private static final Logger logger = LoggerFactory.getLogger(WasherAdminService.class);

    @Autowired
    private WasherServiceClient washerServiceCLient;

    @Autowired
    private AdminWasherRepository adminWasherRepository;

    public List<Map<String, Object>> getAllWashers() {
        logger.info("Fetching all washers from Washer Service.");
        List<Map<String, Object>> responseList = new ArrayList<>();
        List<WasherDTO> washerDTOList = washerServiceCLient.getAllWashers();

        for (WasherDTO washer : washerDTOList) {
            Optional<AdminWasherEntity> adminWasher = adminWasherRepository.findByWasherId(washer.getUserId());
            Map<String, Object> washerData = new HashMap<>();
            washerData.put("washerDetails", washer);
            washerData.put("activeStatus", adminWasher.map(AdminWasherEntity::getActiveStatus).orElse(null));
            responseList.add(washerData);
        }

        logger.info("Fetched and prepared {} washer records.", responseList.size());
        return responseList;
    }

    public Map<String, Object> getWasherById(String id) {
        logger.info("Fetching washer by ID: {}", id);
        WasherDTO washer = washerServiceCLient.getWasherById(id);

        AdminWasherEntity adminWasher = adminWasherRepository.findByWasherId(id)
                .orElseThrow(() -> {
                    logger.error("Washer not found in Admin DB with ID: {}", id);
                    return new RuntimeException("Washer not found in Admin database");
                });

        Map<String, Object> response = new HashMap<>();
        response.put("washerDetails", washer);
        response.put("activeStatus", adminWasher.getActiveStatus());

        logger.info("Washer fetched successfully: {}", response);
        return response;
    }

    public List<AdminWasherEntity> addWasherFromWasherService() {
        logger.info("Adding all washers from Washer Service into Admin DB.");
        List<AdminWasherEntity> list = new ArrayList<>();
        List<WasherDTO> washerDTOList = washerServiceCLient.getAllWashers();

        for (WasherDTO washer : washerDTOList) {
            AdminWasherEntity entity = new AdminWasherEntity();
            entity.setWasherId(washer.getUserId());
            entity.setActiveStatus(true);
            adminWasherRepository.save(entity);
            list.add(entity);
        }

        logger.info("Successfully added {} washers to Admin DB.", list.size());
        return list;
    }

    public AdminWasherEntity updateStatus(String washerId, Boolean activeStatus) {
        AdminWasherEntity entity = adminWasherRepository.findByWasherId(washerId).orElseThrow(() -> {
            logger.error("Customer not found with ID: {}", washerId);
            return new NotFoundException("Customer not found with ID: " + washerId);
        });
        entity.setActiveStatus(activeStatus);
        adminWasherRepository.save(entity);
        return entity;
    }
}
