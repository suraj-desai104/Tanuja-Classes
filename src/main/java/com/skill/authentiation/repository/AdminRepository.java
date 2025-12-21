package com.skill.authentiation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skill.authentiation.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{

	Object findByUsername(String username);

	boolean existsByUsername(String username);

}
