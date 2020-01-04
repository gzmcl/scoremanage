package com.study.scoremanage.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class LoginController {
    @RequestMapping("/bootstrap/myindex")
    public ModelAndView index(){
        return new ModelAndView("/bootstrap3/myindex");
    }

    @RequestMapping("/index2")
    public ModelAndView index2(){
        return new ModelAndView("/index2");
    }

    @RequestMapping("/test")
    public ModelAndView test(){
        log.info("跳转到/test");
        return new ModelAndView("/bootstrap3/t2");
//        return "/bootstrap3/t2";
    }

    /**
     * 自定义登录页面
     * @param error 错误信息显示标识
     * @return
     *
     */
    @GetMapping("/login")
    public ModelAndView login(String error){
        log.info("登录");
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}
