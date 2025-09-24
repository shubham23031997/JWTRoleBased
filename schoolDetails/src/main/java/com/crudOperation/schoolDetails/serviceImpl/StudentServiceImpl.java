package com.crudOperation.schoolDetails.serviceImpl;

import com.crudOperation.schoolDetails.entity.Student;
import com.crudOperation.schoolDetails.exception.StudentNotFoundException;
import com.crudOperation.schoolDetails.repository.StudentRepository;
import com.crudOperation.schoolDetails.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;//constructor based

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Integer id, Student student) {
        Student existingStudent = getStudentById(id);

        existingStudent.setName(student.getName());
        existingStudent.setPercentage(student.getPercentage());
        existingStudent.setBranch(student.getBranch());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }
}
