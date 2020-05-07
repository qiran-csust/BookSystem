package com.java456.controller;

import javax.annotation.Resource;

import com.java456.dao.RoleDao;
import com.java456.entity.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java456.dao.UserDao;
import com.java456.entity.User;
import com.java456.util.CryptographyUtil;

import net.sf.json.JSONObject;

import java.util.Date;


@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserDao userDao;
    @Resource
	private RoleDao roleDao;

    /**
     * /user/login
     */
    @ResponseBody
    @RequestMapping("/login")
    public JSONObject login(String name, String password) throws Exception {
        JSONObject result = new JSONObject();

        //获取 subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, CryptographyUtil.md5(password, "java"));

        try {
            //执行登陆  shiro的登陆
            subject.login(token);
            //执行登陆  shiro的登陆

            result.put("success", true);
            result.put("msg", "登陆成功");
            User user = userDao.findByName(name);

            //把当前用户信息存到session中
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
        } catch (UnknownAccountException e) {
            result.put("success", false);
            result.put("msg", "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            result.put("success", false);
            result.put("msg", "密码错误");
        }
        return result;
    }

	/**
	 * /user/register
	 * 普通用户注册
	 * @param name
	 * @param tname
	 * @param password
	 * @param repassword
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/register")
    public JSONObject register(String name, String tname, String password, String repassword) {
        JSONObject result = new JSONObject();
        if (name == null || "".equals(name)) {
            result.put("success", false);
            result.put("msg", "账号不能为空！");
        } else if (tname == null || "".equals(tname)) {
            result.put("success", false);
            result.put("msg", "真实姓名不能为空！");
        } else if (!password.equals(repassword)) {
            result.put("success", false);
            result.put("msg", "两次密码输入不一致！");
        } else {
            User user = new User();
            user.setName(name);
            user.setTrueName(tname);
            user.setPwd(CryptographyUtil.md5(password, "java"));
            user.setCreateDateTime(new Date());
            user.setOrderNo(2);
            Role role = roleDao.findId(2);
            user.setRole(role);
            // 将信息保存到数据库
            user = userDao.save(user);

            // 执行shiro登录
			//获取 subject
			Subject subject = SecurityUtils.getSubject();
			//封装用户数据
			UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPwd());
			subject.login(token);
			//把当前用户信息存到session中
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);

			result.put("success", true);
			result.put("msg", "注册成功！");
        }
        return result;
    }

    /**
     * /user/getUser
     * 从session中获取当前登录的用户实体对象
     */
    @ResponseBody
    @RequestMapping(value = "/getUser")
    public JSONObject getUser(){
        JSONObject result = new JSONObject();
        Integer roleId = null;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        if(user != null){
            roleId = user.getRole().getId();
        }else{
            roleId = 0;        // 表示当前用户登录状态已失效
        }
        result.put("roleId", roleId);
        return result;
    }


    /**
     * 注销
     * /user/logout
     *
     * @throws Exception
     */
    @RequestMapping("/logout")
    public String logout() throws Exception {
        SecurityUtils.getSubject().logout(); //shiro的退出
        return "redirect:/login";
    }


}
