package com.skill.lecture.repository;

import com.skill.lecture.model.RecordedLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordedLectureRepository extends JpaRepository<RecordedLecture, Long> {

    // Optional: Find all recordings by course
    List<RecordedLecture> findByCourse(String course);

    // Optional: Find all recordings created by a specific admin
    List<RecordedLecture> findByCreatedBy(String createdBy);
}
