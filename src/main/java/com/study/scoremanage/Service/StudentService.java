package com.study.scoremanage.Service;

import com.study.scoremanage.Mapper.StudentMapper;
import com.study.scoremanage.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    final private StudentMapper studentMapper;
    @Autowired
    public StudentService(StudentMapper studentMapper)
    {
        this.studentMapper = studentMapper;
    }

    public int AddStudent(Student student)
    {
        //添加学生信息
        return studentMapper.insertStudent(student);
    }
    public int EditStudentById(Student student)
    {
        //修改学生信息
        return studentMapper.updateStudentById(student);
    }

    public int EditStudentById(Long id,Student student)
    {
        //修改学生信息
        student.setId(id);
        return studentMapper.updateStudentById(student);
    }


    public int  DeleteStudent(Student student)
    {
        //删除学生信息
        return studentMapper.deleteStudent(student);
    }

    public int  DeleteStudentById(Long id)
    {
        //删除学生信息
        return studentMapper.deleteStudentById(id);
    }

    public List<Student> GetStudent(Student student)
    {
        //读取学生信息
        return studentMapper.selectStudent(student);
    }

    public Student GetStudentById(Long id)
    {
        //读取学生信息
        return studentMapper.selectByPrimaryKey(id);
    }
}
