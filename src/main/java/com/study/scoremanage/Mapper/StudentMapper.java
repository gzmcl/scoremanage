package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.Academicrecord;
import com.study.scoremanage.Model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentMapper {
    int deleteStudent(Student student);

    int deleteStudentById(Long id);

    int insertStudent(Student student);

    List<Student> selectStudent(Student student);

    Student selectByPrimaryKey(Long id);

    int updateStudentById(Student student);
}