package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.Academicrecord;
import com.study.scoremanage.Service.AcademicrecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AcademicrecordController {
    final private AcademicrecordService academicrecordservice;
    @Autowired
    public AcademicrecordController(AcademicrecordService academicrecordservice)
    {
        this.academicrecordservice = academicrecordservice;
    }

    //获取课程信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/academicrecordes")
    public List<Academicrecord> getAcademicrecord(Academicrecord academicrecord){
        log.info("测试！");
        log.info(academicrecord.toString());
        return academicrecordservice.selectAcademicrecord(academicrecord);
    }

    //获取课程信息，路径id为准
    @GetMapping("/academicrecordes/{id}")
    public Academicrecord getAcademicrecordById(@PathVariable("id") Long id){
        log.info("测试！");
        return academicrecordservice.selectByPrimaryKey(id);
    }

    //增加课程信息
    @PostMapping("/academicrecordes")
    public int AddAcademicrecord(@RequestBody Academicrecord academicrecord){
        log.info(academicrecord.toString());
        return academicrecordservice.insert(academicrecord);
    }

    //修改课程信息，路径中ID为准
    @PutMapping("/academicrecordes/{id}")
    public int EditAcademicrecordById(@PathVariable("id") Long id, @RequestBody Academicrecord academicrecord)
    {
        academicrecord.setAca_id(id);
        log.info("put测试！");
        log.info(academicrecord.toString());
        return academicrecordservice.updateByPrimaryKeySelective(academicrecord);
    }

    //删除课程信息，字段精确查找为准
//    @DeleteMapping("/academicrecordes")
//    public int DeleteAcademicrecord(@RequestBody Academicrecord academicrecord)
//    {
//        return academicrecordeservice.deleteAcademicrecord(academicrecord);
//    }

    //删除课程信息，路径id为准
    @DeleteMapping("/academicrecordes/{id}")
    public int DeleteAcademicrecordById(@PathVariable("id") Long id)
    {
        return academicrecordservice.deleteById(id);
    }
}
