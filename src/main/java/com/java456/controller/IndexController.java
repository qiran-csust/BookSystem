package com.java456.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.java456.dao.MenuDao;
import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.dao.RoleMenuDao;
import com.java456.entity.BookType;
import com.java456.entity.Menu;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.RoleMenu;
import com.java456.entity.User;
import com.java456.service.MenuService;
import com.java456.util.BrowserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.java456.util.*;

@Controller
public class IndexController {
	
	@Autowired 
	private ServletContext servletContext;
	@Resource
	private MenuDao menuDao;
	@Resource
	private MenuService menuService;
	
	@Resource
	private  RoleMenuDao   roleMenuDao;
	
	@Resource
	private MessageDao messageDao;
	@Resource
	private MessageTypeDao messageTypeDao;
	/**
	 *# 请求首页  
	 */
	@RequestMapping("/")
	public String  index_1(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		return "redirect:/login";
	}
	
	/**
	 *   #请求首页  /index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		return "redirect:/login";
	}
	
	
	/**
	 *    /login
	 *    #后台 用户电脑登陆
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletResponse  res,HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/pc/login/login");
		}else{
			mav.setViewName("/common/s_mode");
		}
		return mav;
	}

	@RequestMapping("/register")
	public ModelAndView register(HttpServletResponse response, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String UserAgent = request.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/pc/register/register");
		}else{
			mav.setViewName("/common/s_mode");
		}
		return mav;
	}
	
	
	
	/**
	 * # 后台主页
	 */
	@RequestMapping("/admin/main")
	public ModelAndView admin_main(HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/admin/main");
		}else{
			mav.setViewName("/common/s_mode");
		}
		//session.getAttribute("currentUser"); 
		
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser"); 
		if(currentUser.getRole()==null){
			return mav;
		}
		
		//根据当前的用户   对应的角色。 展示菜单 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", -1);
		List<Menu> menuList = menuService.list(map , 0, 100);
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
			//当前下当前用户的角色  有没有这个菜单 
			RoleMenu roleMenu=	roleMenuDao.findByRoleIdAndMenuId(currentUser.getRole().getId(), menu.getId());
			if(roleMenu!=null) {
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				node.put("divId", menu.getDivId());
				node.put("children", getChildren(menu.getId(),currentUser.getRole().getId()));
				list.add(node);
			}
		}
		//根据当前的用户   对应的角色。 展示菜单 
		
		mav.addObject("treeList", list);
		
		
		return mav;
	}
	
	
	/**
	 * 辅助方法  辅助 上面的 admin_main（getCheckedTreeMenu）
	 * @param pId
	 * @param roleId
	 * @return
	 */
	public List<JSONObject> getChildren(Integer pId, Integer roleId)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		List<Menu> menuList = menuService.list(map , 0, 100);
		
		List<JSONObject>  list =  new ArrayList<JSONObject>();
		for (Menu menu : menuList) {
			RoleMenu roleMenu=	roleMenuDao.findByRoleIdAndMenuId(roleId, menu.getId());
			if(roleMenu!=null){
				JSONObject node = new JSONObject();
				node.put("id", menu.getId());
				node.put("text", menu.getName());
				node.put("url", menu.getUrl());
				node.put("type", menu.getType());
				node.put("icon", menu.getIcon());
				node.put("divId", menu.getDivId());
				list.add(node);
			}
		}
		return list;
	}
	
	
	
	 
	@RequestMapping("/admin/search")
	public ModelAndView search_main(HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/admin/search");
		}else{
			mav.setViewName("/common/s_mode");
		}
		String source=req.getParameter("source");	//获取html页面搜索框的值
        List<Message> userList =messageDao.seachMessage(source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "查询数据");
		mav.setViewName("/admin/search");
		return mav;
	}
	
	@RequestMapping("/admin/newMessage")
	public ModelAndView newMessage_main(HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		String UserAgent = req.getHeader("User-Agent");
		//判断AppleWebKit 和  Firefox    
		if(BrowserUtil.checkUserAgent(UserAgent)){
			mav.setViewName("/admin/newMessage");
		}else{
			mav.setViewName("/common/s_mode");
		}
        List<Message> userList =messageDao.searchNewMessage();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "查询数据");
		mav.setViewName("/admin/newMessage");
		return mav;
	}
	

}
