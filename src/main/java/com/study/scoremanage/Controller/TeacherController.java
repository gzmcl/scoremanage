package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.Teacher;
import com.study.scoremanage.Service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TeacherController {
    final private TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherService teacherService)
    {
        this.teacherService = teacherService;
    }

    //获取老师信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/teachers")
    public List<Teacher> getTeacher(Teacher teacher){
        log.info("测试！");
        log.info(teacher.toString());
        return teacherService.selectTeacher(teacher);
    }

    //获取老师信息，路径id为准
    @GetMapping("/teachers/{id}")
    public Teacher getTeacherById(@PathVariable("id") Long id){
        log.info("测试！");
        return teacherService.selectByPrimaryKey(id);
    }

    //增加老师信息
    @PostMapping("/teachers")
    public int AddTeacher(@RequestBody Teacher teacher){
        log.info(teacher.toString());
        return teacherService.insert(teacher);
    }

    //修改老师信息，路径中ID为准
    @PutMapping("/teachers/{id}")
    public int EditTeacherById(@PathVariable("id") Long id, @RequestBody Teacher teacher)
    {
        teacher.setTea_id(id);
        log.info("put测试！");
        log.info(teacher.toString());
        return teacherService.updateByPrimaryKeySelective(teacher);
    }

    //删除老师信息，字段精确查找为准
//    @DeleteMapping("/teachers")
//    public int DeleteTeacher(@RequestBody Teacher teacher)
//    {
//        return teacherService.deleteTeacher(teacher);
//    }

    //删除老师信息，路径id为准
    @DeleteMapping("/teachers/{id}")
    public int DeleteTeacherById(@PathVariable("id") Long id)
    {
        return teacherService.deleteById(id);
    }
}
