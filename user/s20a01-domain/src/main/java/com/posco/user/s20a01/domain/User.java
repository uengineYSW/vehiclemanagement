package com.posco.user.s20a01.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;

    private String password;

    private String role;

    private String roleType;

    private Boolean isActive;

    private Date createdAt;

    private String email;

    public void updateUser(
        Long userId,
        String username,
        String password,
        String email,
        String role,
        String roleType,
        Boolean isActive,
        Date createdAt
    ) {
        // 비즈니스 로직 구현
    }
}
