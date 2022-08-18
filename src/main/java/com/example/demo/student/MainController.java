//package com.example.demo.student;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping(path="/demo")
//public class MainController {
//
//    private final StudentService studentService;
//
//    public MainController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @PostMapping(path="/add")
//    public @ResponseBody String addNewStudent (@RequestBody CreateStudentDTO dto){
//            Student s = new Student();
//            s.setName(dto.getName());
//            s.setEmail(dto.getEmail());
//            s.setDob(dto.getDob());
//            this.studentService.addNewstudent(s);
//            return "Saved";
//    }
//
//    @GetMapping(path="/all")
//    public @ResponseBody
//    Iterable<Student> getAllStudents(){
//        return null;
//    }
//}
