package com.posco.vehicle.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterVehicleCommand {

    private String vehicleType;
    private String vehicleName;
}
