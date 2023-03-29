package com.example.upg.repositoriy;


import com.example.upg.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findById(int id);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity>findByPhoneNumberAndEmail(int id, String email);

}
