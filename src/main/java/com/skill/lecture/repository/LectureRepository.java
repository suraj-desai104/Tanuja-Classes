package com.skill.lecture.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.skill.lecture.model.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourse(String course);
}
