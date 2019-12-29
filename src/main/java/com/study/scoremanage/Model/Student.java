package com.study.scoremanage.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Student implements Serializable {
    private Long id;
    private String sno; //学号
    private String name; //姓名
    private String sex; //性别
    private Date birthday; //出生日期
    private String qq; //qq号
    private String phone;
    private String banjiname;//班级名称
    private List<Academicrecord> academicrecords;
    private Date createtime;
    private static final long serialVersionUID = 1L;
}
