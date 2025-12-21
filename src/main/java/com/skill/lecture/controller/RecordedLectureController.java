package com.skill.lecture.controller;

import com.skill.lecture.model.RecordedLecture;
import com.skill.lecture.repository.RecordedLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/recorded-lecture")
public class RecordedLectureController {

    @Autowired
    private RecordedLectureRepository recordedLectureRepository;

    // CREATE a new recorded lecture
    @PostMapping
    public RecordedLecture createRecordedLecture(@RequestBody RecordedLecture lecture) {
        // Optionally, set createdBy from authenticated admin
        // lecture.setCreatedBy(auth.getUsername());
        return recordedLectureRepository.save(lecture);
    }

    // GET all recorded lectures
    @GetMapping
    public List<RecordedLecture> getAllRecordedLectures() {
        return recordedLectureRepository.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public RecordedLecture getRecordedLectureById(@PathVariable Long id) {
        return recordedLectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recorded lecture not found with id " + id));
    }

    // UPDATE recorded lecture
    @PutMapping("/{id}")
    public RecordedLecture updateRecordedLecture(@PathVariable Long id, @RequestBody RecordedLecture lectureDetails) {
        RecordedLecture lecture = recordedLectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recorded lecture not found with id " + id));

        lecture.setTopic(lectureDetails.getTopic());
        lecture.setDescription(lectureDetails.getDescription());
        lecture.setCourse(lectureDetails.getCourse());
        lecture.setVideoLink(lectureDetails.getVideoLink());
        lecture.setDate(lectureDetails.getDate());
        lecture.setCreatedBy(lectureDetails.getCreatedBy()); // optional: keep original createdBy

        return recordedLectureRepository.save(lecture);
    }

    // DELETE recorded lecture
    @DeleteMapping("/{id}")
    public String deleteRecordedLecture(@PathVariable Long id) {
        return recordedLectureRepository.findById(id).map(lecture -> {
            recordedLectureRepository.delete(lecture);
            return "Recorded lecture deleted successfully";
        }).orElse("Recorded lecture not found");
    }
}
