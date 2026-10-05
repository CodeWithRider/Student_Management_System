package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    //constructor injection (Spring automatically provides the repository object
    private final StudentRepository studentRepository;

    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return  studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Student not found with id: "+id));
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existingStudent =
                studentRepository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Student not found by id: "+id));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setAge(student.getAge());
        existingStudent.setCourse(student.getCourse());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if(!studentRepository.existsById(id)){
            throw new RuntimeException("Student not found with id: "+id);
        }

        studentRepository.deleteById(id);
    }
}
