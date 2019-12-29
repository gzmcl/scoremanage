package com.study.scoremanage.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * role_permission
 * @author 
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "handler" })
public class RolePermission implements Serializable {
    private Long roleId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;
}