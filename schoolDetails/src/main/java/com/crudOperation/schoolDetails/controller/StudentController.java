package com.crudOperation.schoolDetails.controller;

import com.crudOperation.schoolDetails.entity.Student;
import com.crudOperation.schoolDetails.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j//for logger
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // private static final Logger logger = (Logger) LoggerFactory.getLogger(StudentController.class);
    //get all students
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")//this we added for role based
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        log.debug(students.get(0).toString());
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);//this is one way to
    }

    //localhost:8080/student/1
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    //localhost:8080/student/add
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String addStudent(@RequestBody Student student) {
        this.studentService.createStudent(student);
        return "record added successfully";
    }

    @PutMapping("/update/{id}")
    public Student updateStudent(@PathVariable int id, Student student) {
        Student existingStudent = studentService.getStudentById(id);
        studentService.updateStudent(id, student);
        return student;

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteStudent(@PathVariable int id) {
        try {
            Student student = this.studentService.getStudentById(id);

            this.studentService.deleteStudent(id);
            return "record deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "record not found";
        }
    }
}

