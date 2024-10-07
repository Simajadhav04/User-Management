package com.app.usersmanagement.repository;


import com.app.usersmanagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
 *author: Sima Jadhav
 */

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

}
