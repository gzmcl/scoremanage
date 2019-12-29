package com.study.scoremanage.Mapper;

import com.study.scoremanage.Model.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MyUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MyUser record);

    int insertSelective(MyUser record);

    MyUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MyUser record);

    int updateByPrimaryKey(MyUser record);

    //手动添加
    List<MyUser> selectMyUser(MyUser myUser);
    int deleteMyUser(MyUser myUser);
    MyUser loadMyUserByUsername(String username);
}