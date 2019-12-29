package com.study.scoremanage.Service;

import com.study.scoremanage.Mapper.TeacherMapper;
import com.study.scoremanage.Model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    final private TeacherMapper teacherMapper;
    @Autowired
    public TeacherService(TeacherMapper teacherMapper)
    {
        this.teacherMapper = teacherMapper;
    }
////
    public int deleteById(Long id)
    {
        return teacherMapper.deleteByPrimaryKey(id);
    }
////
////    public int deleteTeacher(Teacher teacher)
////    {
////        return teacherMapper.deleteTeacher(teacher);
////    }
////
    public int insert(Teacher record)
    {
        return teacherMapper.insertSelective(record);
    }
////
    public Teacher selectByPrimaryKey(Long id)
    {
        return teacherMapper.selectByPrimaryKey(id);
    }
////
    public List<Teacher> selectTeacher(Teacher teacher)
    {
        return teacherMapper.selectTeacher(teacher);
    }
////
    public int updateByPrimaryKeySelective(Teacher record)
    {
        return teacherMapper.updateByPrimaryKeySelective(record);
    }

}
