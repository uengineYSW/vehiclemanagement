package vehiclemanagement.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import vehiclemanagement.DriverApplication;
import vehiclemanagement.domain.DriverInfoCreated;
import vehiclemanagement.domain.DriverInfoDeleted;

@Entity
@Table(name = "DriverInfo_table")
@Data
//<<< DDD / Aggregate Root
public class DriverInfo {

    @Id
    private String companyName;

    private String affiliation;

    private String contact;

    private String permission;

    private Boolean isActive;

    @ElementCollection
    private List<VehicleInfoId> vehicleInfoIds;

    @Embedded
    private UserInfoId userInfoId;

    @PostPersist
    public void onPostPersist() {
        DriverInfoCreated driverInfoCreated = new DriverInfoCreated(this);
        driverInfoCreated.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    @PreRemove
    public void onPreRemove() {
        DriverInfoDeleted driverInfoDeleted = new DriverInfoDeleted(this);
        driverInfoDeleted.publishAfterCommit();
    }

    public static DriverInfoRepository repository() {
        DriverInfoRepository driverInfoRepository = DriverApplication.applicationContext.getBean(
            DriverInfoRepository.class
        );
        return driverInfoRepository;
    }

    //<<< Clean Arch / Port Method
    public void updateDriverInfo(
        UpdateDriverInfoCommand updateDriverInfoCommand
    ) {
        //implement business logic here:

        DriverInfoUpdated driverInfoUpdated = new DriverInfoUpdated(this);
        driverInfoUpdated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
