package com.study.scoremanage.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * academicrecord
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Academicrecord implements Serializable {
    private Long aca_id;

    private String courseid;
//    private Course course;

    private String studentid;
//    private Student student;

    private Integer score;

    private Date createtime;

    private static final long serialVersionUID = 1L;

}