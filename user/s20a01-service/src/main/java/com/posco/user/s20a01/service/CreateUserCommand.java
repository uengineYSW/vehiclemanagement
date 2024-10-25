package com.posco.user.s20a01.service;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CreateUserCommand {

    private String username;
    private String password;
    private String email;
    private String role;
}
