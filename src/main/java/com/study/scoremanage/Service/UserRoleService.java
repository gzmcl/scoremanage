package com.study.scoremanage.Service;

import com.study.scoremanage.Mapper.TeacherMapper;
import com.study.scoremanage.Mapper.UserRoleMapper;
import com.study.scoremanage.Model.MyUser;
import com.study.scoremanage.Model.Role;
import com.study.scoremanage.Model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    final private UserRoleMapper userRoleMapper;
    final private MyUserService myUserService;
    final private RoleService roleService;
    @Autowired
    public UserRoleService(UserRoleMapper userRoleMapper,MyUserService myUserService,RoleService roleService)
    {
        this.userRoleMapper = userRoleMapper;
        this.myUserService = myUserService;
        this.roleService = roleService;
    }

    public int insert(UserRole record)
    {
        return userRoleMapper.insert(record);
    }

    public List<UserRole> selectUserRole(UserRole userRole)
    {
        return userRoleMapper.selectUserRole(userRole);
    }

    public int insertSelective(UserRole record)
    {
        return userRoleMapper.insertSelective(record);
    }

    //给用户授权：1、根据用户ID给用户授予角色，2、根据用户名给用户授予角色
    public int authRoleToUserByUserId(Long userid,String rolename)
    {
        List<Role> roles = roleService.selectRole(Role.builder().name(rolename).build());
        int i=0; //删除计数。
        for(Role role:roles)
        {
            i += userRoleMapper.deleteSelective(UserRole.builder().userId(userid).roleId(role.getId()).build());

        }
        return i;
    }
    public int authRoleToUserByUserName(String username,String rolename)
    {
        List<MyUser> myUsers = myUserService.selectMyUser(MyUser.builder().username(username).build());
        List<Role> roles = roleService.selectRole(Role.builder().name(rolename).build());
        int i=0;//删除计数
        for(MyUser myUser:myUsers)
        {
            for(Role role:roles)
            {
                i += userRoleMapper.insertSelective(UserRole.builder().userId(myUser.getId()).roleId(role.getId()).build());
            }
        }
        return i;
    }

    //删除授权，1、根据用户ID删除角色名授权，2、根据用户名删除角色名授权
    public int delAuthRoleToUserByUserId(Long userid,String rolename)
    {
        List<Role> roles = roleService.selectRole(Role.builder().name(rolename).build());
        if(roles.size()>0)
        {
            Long roleid = roles.get(0).getId();
            return userRoleMapper.insertSelective(UserRole.builder().userId(userid).roleId(roleid).build());
        }
        return 0;
    }
    public int delAuthRoleToUserByUserName(String username,String rolename)
    {
        List<MyUser> myUsers = myUserService.selectMyUser(MyUser.builder().username(username).build());
        List<Role> roles = roleService.selectRole(Role.builder().name(rolename).build());
        if(myUsers.size()>0 && roles.size()>0)
        {
            Long userid = myUsers.get(0).getId();
            Long roleid = roles.get(0).getId();
            return userRoleMapper.insertSelective(UserRole.builder().userId(userid).roleId(roleid).build());
        }
        return 0;
    }
}
