package com.posco.user.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class UpdateUserCommand {

    private Long userId;
    private String username;
    private Password password;
    private Email email;
    private String role;
    private roleType roleType;
    private Boolean isActive;
    private Date createdAt;
}
