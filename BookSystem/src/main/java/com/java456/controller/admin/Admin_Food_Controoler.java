package com.java456.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java456.dao.BookDao;
import com.java456.dao.FoodDao;
import com.java456.dao.MessageDao;
import com.java456.entity.Book;
import com.java456.entity.Food;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.service.BookService;
import com.java456.service.FoodService;
import com.java456.service.MessageService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/food")
public class Admin_Food_Controoler {

	@Resource
	private FoodDao foodDao;
	@Resource
	private FoodService foodService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;
	/**
	 * /admin/food/add
	 */
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(@Valid Food food,@Valid Message message, BindingResult bindingResult, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		if (bindingResult.hasErrors()) {
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		} else {
			Map<String, Object> map = new HashMap<>();
            long total = messageService.getTotal(map);
            int number=(int)total;
            food.setId(number+1);
			food.setCreateDateTime(new Date());
			foodDao.save(food);
			message.setId(food.getId());
            message.setOrderNo(food.getOrderNo());
            message.setAddrString(food.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(food.getImageUrl());
            message.setSource(food.getSource());
            message.setPrice(food.getPrice());
            message.setUrlString(food.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(3);
            
            message.setMessageType(messageType);
    
            messageDao.save(message);
			result.put("success", true);
			result.put("msg", "添加成功");
			return result;
		}
	}
	

	/**
	 *  /admin/food/update
	 */
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject update(@Valid  Food food  ,@Valid Message message,BindingResult bindingResult, HttpServletRequest request)throws Exception {
		JSONObject result = new JSONObject();
		if(bindingResult.hasErrors()){
			result.put("success", false);
			result.put("msg", bindingResult.getFieldError().getDefaultMessage());
			return result;
		}else{
			food.setUpdateDateTime(new Date());
			foodService.update(food);
			message.setUpdateDateTime(new Date());
            message.setOrderNo(food.getOrderNo());
            message.setAddrString(food.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(food.getImageUrl());
            message.setSource(food.getSource());
            message.setPrice(food.getPrice());
            message.setUrlString(food.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(3);
            message.setMessageType(messageType);
            messageService.update(message);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
	}
	

	/**
	 * /admin/food/list
	 * @param page    默认1
	 * @param limit   数据多少
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit, 
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Food> list = foodService.list(map, page-1, limit);
		long total = foodService.getTotal(map);
		
		map.put("data", list);
		map.put("count", total);
		map.put("code", 0);
		map.put("msg", "");
		return map;
	}
	
	
	/**
	 * /admin/food/delete
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		JSONObject result = new JSONObject();

		for (int i = 0; i < idsStr.length; i++) {
			foodDao.deleteById(Integer.parseInt(idsStr[i]));
		}
		result.put("success", true);
		return result;
	}
	
}
