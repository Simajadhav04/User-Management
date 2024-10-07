package com.app.usersmanagement.repository;

import com.app.usersmanagement.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
/*
 *author: Sima Jadhav
 */

public interface PageableRepository extends PagingAndSortingRepository<Users, Integer> {
    Page<Users> findAll(Pageable pageable);
}
