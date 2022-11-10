package com.challenge.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.users.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

}