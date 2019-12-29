package com.study.scoremanage.Service;

import com.study.scoremanage.Mapper.RoleMapper;
import com.study.scoremanage.Mapper.StudentMapper;
import com.study.scoremanage.Model.MyUser;
import com.study.scoremanage.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final private RoleMapper roleMapper;
    @Autowired
    public RoleService(RoleMapper roleMapper)
    {
        this.roleMapper = roleMapper;
    }

    public int deleteByPrimaryKey(Long id)
    {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public int insert(Role record)
    {
        return roleMapper.insert(record);
    }

    public int insertSelective(Role record)
    {
        return roleMapper.insertSelective(record);
    }

    public Role selectByPrimaryKey(Long id)
    {
        return roleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Role record)
    {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Role record)
    {
        return roleMapper.updateByPrimaryKey(record);
    }

    //以下为手动添加内容
    public List<Role> selectRole(Role role)
    {
        return roleMapper.selectRole(role);
    }
    public List<Role> selectRolesByUserId(Long id)
    {
        return roleMapper.selectRolesByUserId(id);
    }

}
