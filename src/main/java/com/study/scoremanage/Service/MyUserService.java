package com.study.scoremanage.Service;

import com.study.scoremanage.Mapper.MyUserMapper;
import com.study.scoremanage.Model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MyUserService implements UserDetailsService {
    final private MyUserMapper myUserMapper;
    @Autowired
    public MyUserService(MyUserMapper myUserMapper)
    {
        this.myUserMapper = myUserMapper;
    }

    public int deleteByPrimaryKey(Long id)
    {
        return myUserMapper.deleteByPrimaryKey(id);
    }

    public int insert(MyUser record)
    {
        log.info("插入记录："+record);
        //密码加密
        BCryptPasswordEncoder encoding = new BCryptPasswordEncoder();
        record.setPassword(encoding.encode(record.getPassword()));
        log.info("密码加密后结果："+record);
        return myUserMapper.insert(record);
    }

    public int insertSelective(MyUser record)
    {
        log.info("插入记录："+record);
        //密码加密
        BCryptPasswordEncoder encoding = new BCryptPasswordEncoder();
        record.setPassword(encoding.encode(record.getPassword()));
        log.info("密码加密后结果："+record);
        return myUserMapper.insertSelective(record);
        //上述添加只添加myuser表中相关属性，未处理record中关于role相关设置。如需完善需添加相关内容

    }

    public MyUser selectByPrimaryKey(Long id)
    {
        return myUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MyUser record)
    {
        return myUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MyUser record)
    {
        return myUserMapper.updateByPrimaryKey(record);
    }

    //以下为手动添加
    public List<MyUser> selectMyUser(MyUser myUser)
    {
        log.info("运行至service"+myUser);
        return myUserMapper.selectMyUser(myUser);
    }

    public int deleteMyUser(MyUser myUser)
    {
        return myUserMapper.deleteMyUser(myUser);
    }


    //覆盖方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("开始登录测试。");
        MyUser myUser = myUserMapper.loadMyUserByUsername(username);
//        log.info("测试登录验证。");
        if(myUser == null)
        {
            throw new UsernameNotFoundException("账户不存在！");
        }else
        {
            log.info("账户存在"+myUser.toString());
        }
        //方法一：隐藏实现了设置roles，重点在于设置roles时需加"ROLE_"前缀。
        // myUser.setRoles(myUser.getRoles());
        return myUser;

        //修改重点。方法二：显示实现接口UserDetails的类，并设置roles为myUser.getAuthorities(),
//        UserDetails	user = new User(myUser.getUsername(), myUser.getPassword(), true, true, true, true,myUser.getAuthorities());
//        return user;
    }
}
