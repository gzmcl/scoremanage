package com.study.scoremanage.Service;

import com.study.scoremanage.Model.Academicrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study.scoremanage.Mapper.AcademicrecordMapper;

import java.util.List;

@Service
public class AcademicrecordService {
    final private AcademicrecordMapper academicrecordMapper;
    @Autowired
    public AcademicrecordService(AcademicrecordMapper academicrecordMapper)
    {
        this.academicrecordMapper = academicrecordMapper;
    }

    ////
    public int deleteById(Long id) {
        return academicrecordMapper.deleteByPrimaryKey(id);
    }

    ////
////    public int deleteAcademicrecord(Academicrecord Academicrecord)
////    {
////        return academicrecordMapper.deleteAcademicrecord(Academicrecord);
////    }
////
    public int insert(Academicrecord record) {
        return academicrecordMapper.insertSelective(record);
    }

    ////
    public Academicrecord selectByPrimaryKey(Long id) {
        return academicrecordMapper.selectByPrimaryKey(id);
    }

    ////
    public List<Academicrecord> selectAcademicrecord(Academicrecord academicrecord) {
        return academicrecordMapper.selectAcademicrecord(academicrecord);
    }

    ////
    public List<Academicrecord> selectByStudentId(Long id) {
        return academicrecordMapper.selectByStudentId(id);
    }

    public List<Academicrecord> selectByCourseId(Long id) {
        return academicrecordMapper.selectByCourseId(id);
    }

    ////
    public int updateByPrimaryKeySelective(Academicrecord record) {
        return academicrecordMapper.updateByPrimaryKeySelective(record);
    }

}