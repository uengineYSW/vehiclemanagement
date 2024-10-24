package com.posco.driver.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterDriverCommand {

    private Long userId;
    private String name;
    private String contact;
}
