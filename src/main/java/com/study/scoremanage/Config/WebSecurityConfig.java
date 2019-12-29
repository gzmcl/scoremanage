package com.study.scoremanage.Config;

import com.study.scoremanage.Service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final private MyUserService myUserService;
    @Autowired
    public WebSecurityConfig(MyUserService myUserService)
    {
        this.myUserService = myUserService;
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(myUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        log.info(http.authorizeRequests().toString());
        http.authorizeRequests()
                //"/guest/**"接口允许所有人访问，包括未登录的人
                .antMatchers("/myusers/**").permitAll()
                //"/students/**"接口允许只能被拥有admin角色的用户访问
                .antMatchers("/students/**").hasRole("admin")
                //"/teachers/**"接口允许只能被拥有admin角色的用户访问
                .antMatchers("/teachers/**").hasRole("admin")
                //允许登录用户访问。
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //登录页面login
                .loginProcessingUrl("/login").permitAll()
                .and()
                //跨站请求伪造不可用
                .csrf().disable();
        //方法二：关闭验证,追加http.csrf().disable()
//        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
//        http.csrf().disable();
    }
}
