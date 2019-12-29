package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.Role;
import com.study.scoremanage.Service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RoleController {
    final private RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService)
    {
        this.roleService = roleService;
    }

    //获取角色信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/roles")
    public List<Role> getRole(Role role){
        log.info("测试！");
        log.info(role.toString());
        return roleService.selectRole(role);
    }

    //获取角色信息，按用户ID查找角色。
    @GetMapping("/roles/byUserId/{id}")
    public List<Role> getRole(@PathVariable("id") Long id){
        log.info("测试！按用户ID查找："+id.toString());
        return roleService.selectRolesByUserId(id);
    }

    //获取角色信息，路径id为准
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable("id") Long id){
        log.info("测试！");
        return roleService.selectByPrimaryKey(id);
    }

    //增加角色信息
    @PostMapping("/roles")
    public int AddRole(@RequestBody Role role){
        log.info(role.toString());
        return roleService.insert(role);
    }

    //修改角色信息，路径中ID为准
    @PutMapping("/roles/{id}")
    public int EditRoleById(@PathVariable("id") Long id, @RequestBody Role role)
    {
        role.setId(id);
        log.info("put测试！");
        log.info(role.toString());
        return roleService.updateByPrimaryKeySelective(role);
    }

    //删除角色信息，路径id为准
    @DeleteMapping("/roles/{id}")
    public int DeleteRoleById(@PathVariable("id") Long id)
    {
        return roleService.deleteByPrimaryKey(id);
    }
}
