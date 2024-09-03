package com.saroj.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saroj.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

}
