package com.java456.controller.houtai;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java456.dao.BookTypeDao;
import com.java456.dao.FoodTypeDao;
import com.java456.entity.BookType;
import com.java456.entity.FoodType;

@Controller
@RequestMapping("/houtai/food/type")
public class HouTai_FoodType_Controller {
	@Resource
	private FoodTypeDao foodTypeDao;
	
	/**
	 * /houtai/food/type/manage
	 */
	@RequestMapping("/manage")
	public ModelAndView manage() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "美食图书类型管理");
		mav.setViewName("/admin/page/foodtype/foodtype_manage");
		return mav;
	}
	
	/**
	 * /houtai/food/type/add
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("btn_text", "添加");
		mav.addObject("save_url", "/admin/food/type/add");
		mav.setViewName("/admin/page/foodtype/add_update");
		return mav;
	}
	
	
	/**
	 * /houtai/food/type/edit?id=1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		FoodType foodType = foodTypeDao.findId(id);
		mav.addObject("foodType", foodType);
		mav.addObject("btn_text", "修改");
		mav.addObject("save_url", "/admin/food/type/update?id=" + id);
		mav.setViewName("/admin/page/foodtype/add_update");
		return mav;
	}
	
	
	
}
