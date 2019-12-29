package com.study.scoremanage.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * teacher
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Teacher implements Serializable {
    private Long tea_id;

    private String tno;

    private String name;

    private String title;

    private Date entrytime;

    private Date createtime;

    private static final long serialVersionUID = 1L;

}