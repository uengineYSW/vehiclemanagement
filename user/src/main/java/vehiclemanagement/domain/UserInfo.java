package vehiclemanagement.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import vehiclemanagement.UserApplication;
import vehiclemanagement.domain.UserInfoCreated;
import vehiclemanagement.domain.UserInfoDeleted;

@Entity
@Table(name = "UserInfo_table")
@Data
//<<< DDD / Aggregate Root
public class UserInfo {

    @Id
    private String name;

    private String juminNum;

    private String birthDate;

    @Embedded
    private Address address;

    @PostPersist
    public void onPostPersist() {
        UserInfoCreated userInfoCreated = new UserInfoCreated(this);
        userInfoCreated.publishAfterCommit();

        UserInfoDeleted userInfoDeleted = new UserInfoDeleted(this);
        userInfoDeleted.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    public static UserInfoRepository repository() {
        UserInfoRepository userInfoRepository = UserApplication.applicationContext.getBean(
            UserInfoRepository.class
        );
        return userInfoRepository;
    }

    public void createUserInfo(CreateUserInfoCommand createUserInfoCommand) {
        //implement business logic here:

        UserInfoCreated userInfoCreated = new UserInfoCreated(this);
        userInfoCreated.publishAfterCommit();
    }

    //<<< Clean Arch / Port Method
    public void updateUserInfo(UpdateUserInfoCommand updateUserInfoCommand) {
        //implement business logic here:

        UserInfoUpdated userInfoUpdated = new UserInfoUpdated(this);
        userInfoUpdated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    public void deleteUserInfo(DeleteUserInfoCommand deleteUserInfoCommand) {
        //implement business logic here:

        UserInfoDeleted userInfoDeleted = new UserInfoDeleted(this);
        userInfoDeleted.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
