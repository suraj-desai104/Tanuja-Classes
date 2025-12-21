package com.skill.lecture.controller;

import com.skill.authentiation.model.Admin;
import com.skill.lecture.model.Lecture;
import com.skill.lecture.repository.LectureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lecture")
public class AdminLectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @PostMapping
    public Lecture createLecture(@RequestBody Lecture lecture, Admin auth) {

        lecture.setCreatedBy(auth.getUsername()); // admin username

        return lectureRepository.save(lecture);
    }

    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }
    
 // DELETE /api/admin/lecture/{id}
    @DeleteMapping("/{id}")
    public String deleteLecture(@PathVariable Long id) {
        return lectureRepository.findById(id).map(lecture -> {
            lectureRepository.delete(lecture);
            return "Lecture deleted successfully";
        }).orElse("Lecture not found");
    }
    
 // PUT /api/admin/lecture/{id}
    @PutMapping("/{id}")
    public Lecture updateLecture(@PathVariable Long id, @RequestBody Lecture updatedLecture) {
        return lectureRepository.findById(id).map(lecture -> {
            // Update fields
            lecture.setTopic(updatedLecture.getTopic());
            lecture.setDescription(updatedLecture.getDescription());
            lecture.setMeetingLink(updatedLecture.getMeetingLink());
            lecture.setDate(updatedLecture.getDate());
            lecture.setTime(updatedLecture.getTime());
            lecture.setCourse(updatedLecture.getCourse());
            // createdBy usually not updated, keep as is

            return lectureRepository.save(lecture);
        }).orElseThrow(() -> new RuntimeException("Lecture not found with id " + id));
    }


}
