package com.posco.driver.s20a01.domain;

import com.posco.driver.s20a01.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "drivers", path = "drivers")
public interface DriverRepository extends JpaRepository<Driver, Long> {}
