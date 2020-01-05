package com.study.scoremanage.Config;

import com.study.scoremanage.Service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final private MyUserService myUserService;
    private final MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;
    @Autowired
    public WebSecurityConfig(MyUserService myUserService,MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler)
    {
        this.myUserService = myUserService;
        this.myAuthenctiationSuccessHandler = myAuthenctiationSuccessHandler;
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
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/bootstrap3/images/**","/bootstrap3/lib/**","/bootstrap3/javascripts/**","/bootstrap3/stylesheets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        log.info(http.authorizeRequests().toString());

        http.cors().and().csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/**/*.css","/bootstrap3/**","/bootstrap4/**").permitAll()
                //使用form表单post方式进行登录w
                .and().formLogin()
                //登录页面为自定义的登录页面
                .loginPage("/login")
                //设置登录成功后跳转到登录前页面
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        RequestCache cache = new HttpSessionRequestCache();
                        SavedRequest savedRequest = cache.getRequest(request, response);
                        String url = savedRequest.getRedirectUrl();
                        response.sendRedirect(url);
                    }
                })
                .permitAll()
                .and()
                //允许不登陆就可以访问的方法，多个用逗号分隔
//                .authorizeRequests().antMatchers("/bootstrap3/images/**","/bootstrap3/lib/**","/bootstrap3/javascripts/**","/bootstrap3/stylesheets/**").permitAll()
                //其他的需要授权后访问
                .authorizeRequests().anyRequest().authenticated();
//
//        //session管理,失效后跳转
//        http.sessionManagement().invalidSessionUrl("/login");
//        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
//        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
//        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
//        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
//        //退出时情况cookies
//        http.logout().deleteCookies("JESSIONID");
//        //解决中文乱码问题
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8"); filter.setForceEncoding(true);
//        //
//        http.addFilterBefore(filter,CsrfFilter.class);
        //方法三：
//        http.authorizeRequests()
//                //"/guest/**"接口允许所有人访问，包括未登录的人
//                .antMatchers("/myusers/**","/myLogin",
//                        "/login.html","/authentication/require","/authentication/form").permitAll()
//                //"/students/**"接口允许只能被拥有admin角色的用户访问
//                .antMatchers("/students/**").hasRole("admin")
//                //"/teachers/**"接口允许只能被拥有admin角色的用户访问
//                .antMatchers("/teachers/**").hasRole("admin")
//                //允许登录用户访问。
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()// 表单登录  来身份认证
//                .loginPage("/myLogin").successHandler(myAuthenctiationSuccessHandler)// 自定义登录页面
//                .loginProcessingUrl("/authentication/form")// 自定义登录路径
//                .and()
//                //跨站请求伪造不可用
//                .csrf().disable();
        //方法二：关闭验证,追加http.csrf().disable()
//        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
//        http.csrf().disable();
    }
}
