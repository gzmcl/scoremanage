package com.study.scoremanage.Controller;

import com.study.scoremanage.Model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class LoginController {
    @GetMapping("login")
    public String login(){
        log.info("自定义登录页面测试！");
        return "login";
    }
}
