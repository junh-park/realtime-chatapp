package com.jun.chatapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByUsername(String username);

}
