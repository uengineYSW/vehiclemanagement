package com.posco.driver.s20a01.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Driver_table")
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long driverId;

    private Long userId;

    private String name;

    private String company;

    private String department;

    private String contact;

    private String employeeId;

    private String permission;

    private String carRegistration;

    private Boolean isActive;

    private String approverId;

    private Date createdAt;

    public void updateDriver(
        Long userId,
        String name,
        String company,
        String department,
        String contact,
        String employeeId,
        String permission,
        String carRegistration,
        Boolean isActive,
        String approverId,
        Date createdAt
    ) {
        // 비즈니스 로직 구현
    }
}
