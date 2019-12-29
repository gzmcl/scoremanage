package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.MyUser;
import com.study.scoremanage.Service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class MyUserController {
    final private MyUserService myUserService;
    @Autowired
    public MyUserController(MyUserService myUserService)
    {
        this.myUserService = myUserService;
    }

    //获取用户信息，路径id为准
    @GetMapping("/myusers/{id}")
    public MyUser getMyUserById(@PathVariable("id") Long id){
        log.info("测试！");
        return myUserService.selectByPrimaryKey(id);
    }


    //增加用户信息
    @PostMapping("/myusers")
    public int AddMyUser(@RequestBody MyUser myUser){
        log.info("添加用户"+myUser.toString());
        return myUserService.insertSelective(myUser);
    }

    //修改用户信息，以ID为准
    @PutMapping("/myusers")
    public int UpdateMyUserById(@RequestBody MyUser myUser)
    {
        log.info("put测试！");
        log.info(myUser.toString());
        return myUserService.updateByPrimaryKeySelective(myUser);
    }

    //修改用户信息，路径中ID为准
    @PutMapping("/myusers/{id}")
    public int UpdateMyUserById(@PathVariable("id") Long id, @RequestBody MyUser myUser)
    {
        log.info("put测试！");
        log.info(myUser.toString());
        myUser.setId(id);
        return myUserService.updateByPrimaryKeySelective(myUser);
    }

    //删除用户信息，字段精确查找为准
    @DeleteMapping("/myusers")
    public int DeleteMyUser(@RequestBody MyUser myUser)
    {
        return myUserService.deleteMyUser(myUser);
    }

    //删除用户信息，路径id为准
    @DeleteMapping("/myusers/{id}")
    public int DeleteMyUserById(@PathVariable("id") Long id)
    {
        return myUserService.deleteByPrimaryKey(id);
    }



    //////////////////////////////////////////////////////////////
    //获取用户信息，字段精确查找，无参数为查询全部信息
    @GetMapping("/myusers")
    public List<MyUser> getMyUser(MyUser myUser)
    {
        log.info("测试！");
        log.info(myUser.toString());
        return myUserService.selectMyUser(myUser);
    }
}
