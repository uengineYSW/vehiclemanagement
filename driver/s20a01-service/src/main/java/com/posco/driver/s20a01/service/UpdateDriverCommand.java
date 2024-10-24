package com.posco.driver.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UpdateDriverCommand {

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
}
