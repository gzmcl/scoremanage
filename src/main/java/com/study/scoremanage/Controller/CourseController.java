package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.Course;
import com.study.scoremanage.Service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CourseController {
    final private CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService)
    {
        this.courseService = courseService;
    }

    //获取课程信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/courses")
    public List<Course> getCourse(Course Course){
        log.info("测试！");
        log.info(Course.toString());
        return courseService.selectCourse(Course);
    }

    //获取课程信息，路径id为准
    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable("id") Long id){
        log.info("测试！");
        return courseService.selectByPrimaryKey(id);
    }
//
    //增加课程信息
    @PostMapping("/courses")
    public int AddCourse(@RequestBody Course Course){
        log.info(Course.toString());
        return courseService.insert(Course);
    }

    //修改课程信息，路径中ID为准
    @PutMapping("/courses/{id}")
    public int EditCourseById(@PathVariable("id") Long id, @RequestBody Course Course)
    {
        Course.setCou_id(id);
        log.info("put测试！");
        log.info(Course.toString());
        return courseService.updateByPrimaryKeySelective(Course);
    }


    //删除课程信息，路径id为准
    @DeleteMapping("/courses/{id}")
    public int DeleteCourseById(@PathVariable("id") Long id)
    {
        return courseService.deleteById(id);
    }
}
