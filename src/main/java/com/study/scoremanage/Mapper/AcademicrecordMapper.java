package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.Academicrecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AcademicrecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Academicrecord record);

    int insertSelective(Academicrecord record);

    Academicrecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Academicrecord record);

    //以下为手动添加内容
    List<Academicrecord> selectAcademicrecord(Academicrecord record);

    List<Academicrecord> selectByStudentId(Long id);

    List<Academicrecord> selectByCourseId(Long id);
}