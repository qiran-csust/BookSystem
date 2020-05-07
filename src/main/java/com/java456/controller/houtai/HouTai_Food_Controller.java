package com.java456.controller.houtai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java456.dao.BookDao;
import com.java456.dao.BookTypeDao;
import com.java456.dao.FoodDao;
import com.java456.dao.FoodTypeDao;
import com.java456.dao.MessageDao;
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
	
	
	
	
	
}
