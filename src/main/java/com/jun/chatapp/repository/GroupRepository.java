package com.jun.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.chatapp.domain.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer>{

}
