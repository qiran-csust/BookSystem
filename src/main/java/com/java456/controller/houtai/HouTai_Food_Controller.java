package com.java456.controller.houtai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java456.dao.BankDao;
import com.java456.dao.BookDao;
import com.java456.dao.BookTypeDao;
import com.java456.dao.FoodDao;
import com.java456.dao.FoodTypeDao;
import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.entity.Bank;
import com.java456.entity.BankType;
import com.java456.entity.Book;
import com.java456.entity.BookType;
import com.java456.entity.Food;
import com.java456.entity.FoodType;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.service.BookService;
import com.java456.service.FoodService;

@Controller
@RequestMapping("/houtai/food")
public class HouTai_Food_Controller {

	@Resource
	private FoodDao foodDao;
	@Resource
	private FoodService foodService;
	@Resource
	private FoodTypeDao foodTypeDao;
    @Resource
    private MessageDao messageDao;
	@Resource
	private MessageTypeDao messageTypeDao;
	/**
	 * /houtai/food/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "美食管理");
        List<Message> messages = messageDao.findAllFood();
        foodDao.deleteAll();
        List<Food> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Food food =new Food();
        	food.setId(ss.getId());
        	food.setAddrString(ss.getAddrString());
        	food.setCreateDateTime(new Date());
        	food.setImageUrl(ss.getImageUrl());
        	food.setSource(ss.getSource());
        	food.setPrice(ss.getPrice());
        	food.setUrlString(ss.getUrlString());
        	food.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		foodDao.saveAll(data);
                data.clear();
            }
        	data.add(food);
        }
        if(!data.isEmpty()) {
   		 	foodDao.saveAll(data);
        }
		mav.setViewName("/admin/page/food/food_manage");
		return mav;
	}
	
	/**
	 * /houtai/food/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<FoodType> list = foodTypeDao.findAll(pageable);
		List<FoodType> foodTypeList = list.getContent();//拿到list集合
		mav.addObject("foodTypeList", foodTypeList);
		
		
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/food/add");
		mav.setViewName("/admin/page/food/add_update");
		return mav;
	}
	
	
	/**
	 * /houtai/food/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<FoodType> list = foodTypeDao.findAll(pageable);
		List<FoodType> foodTypeList = list.getContent();//拿到list集合
		mav.addObject("foodTypeList", foodTypeList);
		
		
		Food food = foodDao.findId(id);
		mav.addObject("food", food);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/food/update?id=" + id);
		mav.setViewName("/admin/page/food/add_update");
		return mav;
	}
	/**
	 * /houtai/food/sort
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sort")
	public ModelAndView sort() throws Exception {
		ModelAndView mav = new ModelAndView();
		List<Food> userList =foodDao.FoodOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<FoodType> list = foodTypeDao.findAll(pageable);
		List<FoodType> foodTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", foodTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "排序");
		mav.setViewName("/admin/Sort");
		return mav;

	}
	/**
	 * /houtai/food/select
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select")
	public ModelAndView select(Model model,HttpServletResponse  res,HttpServletRequest req,HttpSession session) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		String source=req.getParameter("source");	//获取html页面搜索框的值
		Integer message_type_id=3;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","food");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
	
	
	
}
