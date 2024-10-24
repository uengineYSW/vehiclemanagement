package com.posco.vehicle.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UpdateVehicleCommand {

    private Long vehicleId;
    private String vehicleType;
    private String vehicleName;
    private String usage;
    private String ownershipCompany;
    private Date contractPeriod;
    private String ownershipType;
    private String accidentType;
    private String operatingDepartment;
    private Date registrationDate;
    private Integer mileage;
    private Boolean isInUse;
    private Date usagePeriodStart;
    private Date usagePeriodEnd;
    private Date engineOilCheck;
    private Date engineOilNextCheck;
    private Date createdAt;
}
