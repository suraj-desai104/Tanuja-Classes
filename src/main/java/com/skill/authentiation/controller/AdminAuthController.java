package com.skill.authentiation.controller;

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

import com.skill.authentiation.dto.AdminRegistrationRequest;
import com.skill.authentiation.dto.AuthRequest;
import com.skill.authentiation.dto.StudentRegistrationRequest;
import com.skill.authentiation.model.Admin;
import com.skill.authentiation.model.Student;
import com.skill.authentiation.repository.AdminRepository;
import com.skill.authentiation.repository.StudentRepository;
import com.skill.security.JwtUtil;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private  StudentRepository studentRepository;
    private final JwtUtil jwtUtil;

    public AdminAuthController(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    
   
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {

        // Fetch student by ID
    	System.out.println(id);
        Admin student = adminRepository.findById(id).orElse(null);

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
                "enabled", student.isEnabled()
        );

        return ResponseEntity.ok(profile);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Admin admin = (Admin) adminRepository.findByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(admin.getUsername(), "ADMIN");

        return ResponseEntity.ok(Map.of("token", token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRegistrationRequest request) {

        if (adminRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);

        return ResponseEntity.ok(Map.of(
                "message", "Admin registered successfully",
                "username", admin.getUsername()
        ));
    }
    
    @PostMapping("/register-student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegistrationRequest request) {

        String fullName = request.getFullName().trim();
        String phone = request.getPhone().trim();

        // Split name
        String[] parts = fullName.split(" ");
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        // Capitalize
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

        // Last 3 digits of phone
        String lastThreeDigits = phone.substring(phone.length() - 3);

        // Username format
        String username = firstName + "." + lastName + "@" + lastThreeDigits;
            
        Student existingUser  =studentRepository.findByUsername(username);
        // Generate random password
        String password = RandomStringUtils.randomAlphanumeric(8);
if(existingUser!=null) {
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setStandard(request.getStandard());
        student.setCourse(request.getCourse());

        studentRepository.save(student);

        return ResponseEntity.ok(Map.of(
                "username", username,
                "password", password
        ));
}else {
	return ResponseEntity.ok("User Alrady register by this name and also phone try another phone number");
}
    }

}
