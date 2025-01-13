package com.bank.bank_projecet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.bank_projecet.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    //@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = ?1")
    public boolean existsByEmail(String email);

}
