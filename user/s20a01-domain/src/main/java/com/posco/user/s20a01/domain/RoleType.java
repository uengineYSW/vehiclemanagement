package com.posco.user.s20a01.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

public enum RoleType {
    관리자,
    운전자,
}
