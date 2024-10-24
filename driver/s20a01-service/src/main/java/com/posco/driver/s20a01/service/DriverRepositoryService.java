package com.posco.driver.s20a01.service;

import com.posco.driver.s20a01.domain.Driver;
import com.posco.driver.s20a01.domain.DriverRepository;
import com.posco.driver.s20a01.service.DriverDeleteCommand;
import com.posco.driver.s20a01.service.RegisterDriverCommand;
import com.posco.driver.s20a01.service.UpdateDriverCommand;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/drivers")
@Service
@Transactional
public class DriverRepositoryService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverRepositoryService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @RequestMapping(value = "/{id}/updateDriver", method = RequestMethod.POST)
    public Driver updateDriver(UpdateDriverCommand updateDriverCommand) {
        Driver driver = driverRepository
            .findById(updateDriverCommand.getDriverId())
            .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        // Map command fields to method parameters
        driver.updateDriver(
            updateDriverCommand.getDriverId(),
            updateDriverCommand.getUserId(),
            updateDriverCommand.getName(),
            updateDriverCommand.getCompany(),
            updateDriverCommand.getDepartment(),
            updateDriverCommand.getContact(),
            updateDriverCommand.getEmployeeId(),
            updateDriverCommand.getPermission(),
            updateDriverCommand.getCarRegistration(),
            updateDriverCommand.getIsActive(),
            updateDriverCommand.getApproverId(),
            updateDriverCommand.getCreatedAt()
        );

        // 레포지토리에 저장
        return driverRepository.save(driver);
    }
}
