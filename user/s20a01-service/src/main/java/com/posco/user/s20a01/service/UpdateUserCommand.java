package com.posco.user.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UpdateUserCommand {

    private Long userId;
    private String username;
    private String password;
    private String email;
    private String role;
    private String roleType;
    private Boolean isActive;
    private Date createdAt;
}
