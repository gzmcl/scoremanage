package com.study.scoremanage.Controller;


import com.study.scoremanage.Model.Student;
import com.study.scoremanage.Service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
//@RequestMapping("/users")
public class StudentController {
    final private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    //获取学生信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/students")
    public List<Student> getStudent(Student student){
        log.info("测试！");
        log.info(student.toString());
        return studentService.GetStudent(student);
    }

    //获取学生信息，路径id为准
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable("id") Long id){
        log.info("测试！");
        return studentService.GetStudentById(id);
    }

    //增加学生信息
    @PostMapping("/students")
    public int AddStudent(@RequestBody Student student){
        log.info(student.toString());
        return studentService.AddStudent(student);
    }

    //修改学生信息，以ID为准
    @PutMapping("/students")
    public int EditStudentById(@RequestBody Student student)
    {
        log.info("put测试！");
        log.info(student.toString());
        return studentService.EditStudentById(student);
    }

    //修改学生信息，路径中ID为准
    @PutMapping("/students/{id}")
    public int EditStudentById(@PathVariable("id") Long id,@RequestBody Student student)
    {
        log.info("put测试！");
        log.info(student.toString());
        return studentService.EditStudentById(id,student);
    }

    //删除学生信息，字段精确查找为准
    @DeleteMapping("/students")
    public int DeleteStudent(@RequestBody Student student)
    {
        return studentService.DeleteStudent(student);
    }

    //删除学生信息，路径id为准
    @DeleteMapping("/students/{id}")
    public int DeleteStudentById(@PathVariable("id") Long id)
    {
        return studentService.DeleteStudentById(id);
    }
//
}
