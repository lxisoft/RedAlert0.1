package com.lxisoft.redalert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lxisoft.redalert.domain.UserRegistration;

public interface UserRepository extends JpaRepository<UserRegistration, Integer> {

UserRegistration findByEmail(String email);

UserRegistration findByPassword(String password);

}
