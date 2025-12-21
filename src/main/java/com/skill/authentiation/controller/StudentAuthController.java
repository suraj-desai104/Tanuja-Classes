package com.skill.authentiation.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skill.authentiation.dto.AuthRequest;
import com.skill.authentiation.dto.ChangePasswordRequest;
import com.skill.authentiation.dto.StudentRegistrationRequest;
import com.skill.authentiation.model.Admin;
import com.skill.authentiation.model.Student;
import com.skill.authentiation.repository.AdminRepository;
import com.skill.authentiation.repository.StudentRepository;
import com.skill.lecture.model.Lecture;
import com.skill.lecture.repository.LectureRepository;
import com.skill.security.JwtUtil;

@RestController
@RequestMapping("/api/student")
public class StudentAuthController {

	@Autowired
	  private  AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public StudentAuthController(StudentRepository studentRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {

        Student student = studentRepository.findById(request.getId()).orElse(null);

        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        // Check old password
        if (!passwordEncoder.matches(request.getOldPassword(), student.getPassword())) {
            return ResponseEntity.status(401).body("Old password is incorrect");
        }

        // Update password
        student.setPassword(passwordEncoder.encode(request.getNewPassword()));
        studentRepository.save(student);

        return ResponseEntity.ok(
            Map.of("message", "Password changed successfully")
        );
    }

    
    
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {

        // Fetch student by ID
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntity.status(404).body("Student not found");
        }

        // Return student profile (omit password)
        Map<String, Object> profile = Map.of(
                "id", student.getId(),
                "username", student.getUsername(),
                "fullName", student.getFullName(),
                "email", student.getEmail(),
                "phone", student.getPhone(),
                "standard", student.getStandard(),
                "course", student.getCourse(),
                "enabled", student.isEnabled()
        );

        return ResponseEntity.ok(profile);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    	
    	 Admin admin = (Admin) adminRepository.findByUsername(request.getUsername());
    	 
    	 if(admin!=null) {

         if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
             return ResponseEntity.status(401).body("Invalid credentials");
         }

         String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN");

         Map<String, Object> response = Map.of(
        		 "id", admin.getId(),
                 "token", token,
                 "role", "ADMIN"
             );
         return ResponseEntity.ok(response);
    	
    	 }else {
        Student student = (Student) studentRepository.findByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(student.getUsername(), "STUDENT");
        Map<String, Object> response = Map.of(
        		"id", student.getId(),
                "token", token,
                "role", "STUDENT"
            );
        return ResponseEntity.ok(response);
    	 }
    }
    
   
    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping
    public List<Lecture> getStudentLectures( ) {

//        Student student = studentRepository.findByUsername(auth.getName());
System.out.println("++++++++++++");
        return lectureRepository.findAll();
    }
}
