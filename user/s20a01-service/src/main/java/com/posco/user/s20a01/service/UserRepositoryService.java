package com.posco.user.s20a01.service;

import com.posco.user.s20a01.domain.User;
import com.posco.user.s20a01.domain.UserRepository;
import com.posco.user.s20a01.service.CreateUserCommand;
import com.posco.user.s20a01.service.DeleteUserCommand;
import com.posco.user.s20a01.service.UpdateUserCommand;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@Service
@Transactional
public class UserRepositoryService {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{id}/updateUser", method = RequestMethod.POST)
    public User updateUser(UpdateUserCommand updateUserCommand) {
        User user = userRepository
            .findById(updateUserCommand.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Map command fields to method parameters
        user.updateUser(
            updateUserCommand.getUserId(),
            updateUserCommand.getUsername(),
            updateUserCommand.getPassword(),
            updateUserCommand.getEmail(),
            updateUserCommand.getRole(),
            updateUserCommand.getRoleType(),
            updateUserCommand.getIsActive(),
            updateUserCommand.getCreatedAt()
        );

        // 레포지토리에 저장
        return userRepository.save(user);
    }
}
