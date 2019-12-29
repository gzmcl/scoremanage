package com.study.scoremanage.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * course
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Course implements Serializable {
    private Long cou_id;
    private String coursename;
    private String term;
    private String banjiname;
    private Teacher teacher;
    private List<Academicrecord> academicrecords;
    private Date createtime;
    private static final long serialVersionUID = 1L;
}