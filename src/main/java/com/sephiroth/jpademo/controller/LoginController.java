package com.sephiroth.jpademo.controller;

import com.sephiroth.jpademo.model.SysUser.Inlogin;
import com.sephiroth.jpademo.service.ServiceSysRole;
import com.sephiroth.jpademo.service.ServiceSysUser;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: 吴占超
 * @Description:
 * @Date: Create in 10:05 2018/2/5
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private ServiceSysUser serviceSysUser;

    @Autowired
    private ServiceSysRole serviceSysRole;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    // @ResponseBody
    public String login(@Valid Inlogin user, HttpServletRequest request) {

        val t = serviceSysRole.findByUser(user.getUsername());

        val ts = serviceSysUser.findByUserName(user.getUsername());
        //当前Subject
        Subject currentUser = SecurityUtils.getSubject();
        //加密（md5+盐），返回一个32位的字符串小写
        // String md5Pwd=new Md5Hash(user.getPassword(),user.getUsername()).toString();
        //传递token给shiro的realm
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            currentUser.login(token);
            return "index";

        } catch (AuthenticationException e) {//登录失败
            request.setAttribute("msg", "用户名和密码错误");
        }
        return "login";
    }

    /**
     *  @Author: 吴占超
     *  @Description:
     *  @Date:  21:33 2018/2/5
     *  @return 登陆页
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
