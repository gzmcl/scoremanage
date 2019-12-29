package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRoleMapper {
    int insert(UserRole record);

    List<UserRole> selectUserRole(UserRole userRole);

    int insertSelective(UserRole record);

    int deleteSelective(UserRole userRole);
}