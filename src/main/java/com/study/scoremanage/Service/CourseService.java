package com.study.scoremanage.Service;

import com.study.scoremanage.Model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study.scoremanage.Mapper.CourseMapper;

import java.util.List;

@Service
public class CourseService {
    final private CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseMapper courseMapper)
    {
        this.courseMapper = courseMapper;
    }

    ////
    public int deleteById(Long id) {
        return courseMapper.deleteByPrimaryKey(id);
    }


    public int insert(Course record) {
        return courseMapper.insertSelective(record);
    }

    ////
    public Course selectByPrimaryKey(Long id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    ////
    public List<Course> selectCourse(Course Course) {
        return courseMapper.selectCourse(Course);
    }

    ////
    public int updateByPrimaryKeySelective(Course record) {
        return courseMapper.updateByPrimaryKeySelective(record);
    }

}