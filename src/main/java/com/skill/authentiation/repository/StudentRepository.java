package com.skill.authentiation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skill.authentiation.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByUsername(String username);

}
