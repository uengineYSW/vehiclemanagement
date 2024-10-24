package com.posco.vehicle.s20a01.service;

import com.posco.vehicle.s20a01.domain.Vehicle;
import com.posco.vehicle.s20a01.domain.VehicleRepository;
import com.posco.vehicle.s20a01.service.DeleteVehicleCommand;
import com.posco.vehicle.s20a01.service.RegisterVehicleCommand;
import com.posco.vehicle.s20a01.service.UpdateVehicleCommand;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vehicles")
@Service
@Transactional
public class VehicleRepositoryService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleRepositoryService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @RequestMapping(value = "/{id}/updateVehicle", method = RequestMethod.POST)
    public Vehicle updateVehicle(UpdateVehicleCommand updateVehicleCommand) {
        Vehicle vehicle = vehicleRepository
            .findById(updateVehicleCommand.getVehicleId())
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found")
            );

        // Map command fields to method parameters
        vehicle.updateVehicle(
            updateVehicleCommand.getVehicleId(),
            updateVehicleCommand.getVehicleType(),
            updateVehicleCommand.getVehicleName(),
            updateVehicleCommand.getUsage(),
            updateVehicleCommand.getOwnershipCompany(),
            updateVehicleCommand.getContractPeriod(),
            updateVehicleCommand.getOwnershipType(),
            updateVehicleCommand.getAccidentType(),
            updateVehicleCommand.getOperatingDepartment(),
            updateVehicleCommand.getRegistrationDate(),
            updateVehicleCommand.getMileage(),
            updateVehicleCommand.getIsInUse(),
            updateVehicleCommand.getUsagePeriodStart(),
            updateVehicleCommand.getUsagePeriodEnd(),
            updateVehicleCommand.getEngineOilCheck(),
            updateVehicleCommand.getEngineOilNextCheck(),
            updateVehicleCommand.getCreatedAt()
        );

        // 레포지토리에 저장
        return vehicleRepository.save(vehicle);
    }
}
