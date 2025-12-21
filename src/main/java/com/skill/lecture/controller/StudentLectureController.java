package com.skill.lecture.controller;

import com.skill.authentiation.model.Student;
import com.skill.authentiation.repository.StudentRepository;
import com.skill.lecture.model.Lecture;
import com.skill.lecture.repository.LectureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/lecture")
public class StudentLectureController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping
    public List<Lecture> getStudentLectures( ) {

//        Student student = studentRepository.findByUsername(auth.getName());
System.out.println("++++++++++++");
        return lectureRepository.findAll();
    }
}
