package com.posco.vehicle.s20a01.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Vehicle_table")
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void updateVehicle(
        Long vehicleId,
        String vehicleType,
        String vehicleName,
        String usage,
        String ownershipCompany,
        Date contractPeriod,
        String ownershipType,
        String accidentType,
        String operatingDepartment,
        Date registrationDate,
        Integer mileage,
        Boolean isInUse,
        Date usagePeriodStart,
        Date usagePeriodEnd,
        Date engineOilCheck,
        Date engineOilNextCheck,
        Date createdAt
    ) {
        // 비즈니스 로직 구현
    }
}
