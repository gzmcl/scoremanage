package com.study.scoremanage.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * permission
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class Permission implements Serializable {
    private Long id;

    private String url;

    private String name;

    private String description;

    private Long pid;

    private static final long serialVersionUID = 1L;
}