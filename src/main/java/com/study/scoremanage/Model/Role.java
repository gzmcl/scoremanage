package com.study.scoremanage.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * role
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Role implements Serializable {
    private Long id;

    private String name;

    private String nameZh;

    private static final long serialVersionUID = 1L;

}