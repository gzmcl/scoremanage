package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.Academicrecord;
import com.study.scoremanage.Model.Course;
import com.study.scoremanage.Model.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Long id);

    List<Course> selectCourse(Course record);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}