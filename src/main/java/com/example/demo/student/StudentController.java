package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

     @PostMapping(path="/")
    public @ResponseBody String addNewStudent (@RequestBody CreateStudentDTO dto){
        Student s = new Student();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setDob(dto.getDob());
        s.setAge(dto.getDob());
        this.studentService.addNewstudent(s);
        return "Saved";
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }


    //new(solution)
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
    }
}

