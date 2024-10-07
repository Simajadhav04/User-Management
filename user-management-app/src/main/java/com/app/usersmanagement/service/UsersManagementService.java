package com.app.usersmanagement.service;

import com.app.usersmanagement.entity.Users;
import com.app.usersmanagement.utils.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersManagementService{

    UserUtils register(UserUtils reg);

    UserUtils login(UserUtils req);

    UserUtils refreshToken(UserUtils req);

    UserUtils getAllUsers();

    Page<Users> getAllUsers(Pageable pageable);

    UserUtils getUsersById(Integer userId);

    UserUtils updateUser(Integer userId, Users reqres);

    UserUtils getMyInfo(String email);

    UserUtils deleteUser(Integer userId);
}
