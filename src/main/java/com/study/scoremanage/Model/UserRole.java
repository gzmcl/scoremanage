package com.study.scoremanage.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * user_role
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class UserRole implements Serializable {
    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}