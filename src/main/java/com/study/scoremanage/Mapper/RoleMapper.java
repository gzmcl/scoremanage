package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    //以下为手动添加内容
    List<Role> selectRole(Role role);
    List<Role> selectRolesByUserId(Long id);
    List<Role> selectRolesByRoleName(String rolename);
}