package com.java456.controller.houtai;

import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.dao.TravelDao;
import com.java456.dao.TravelTypeDao;
import com.java456.entity.Food;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Traffic;
import com.java456.entity.TrafficType;
import com.java456.entity.Travel;
import com.java456.entity.TravelType;
import com.java456.service.TravelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/houtai/travel")
public class HouTai_Travel_Controller {
    @Resource
    private TravelDao travelDao;
    @Resource
    private TravelService travelService;
    @Resource
    private TravelTypeDao travelTypeDao;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageTypeDao messageTypeDao;
    /**
     * /houtai/travel/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "旅游管理");
        List<Message> messages = messageDao.findAllTravel();
        travelDao.deleteAll();
        List<Travel> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Travel travel =new Travel();
        	travel.setId(ss.getId());
        	travel.setId(ss.getOrderNo());
        	travel.setAddrString(ss.getAddrString());
        	travel.setCreateDateTime(new Date());
        	travel.setImageUrl(ss.getImageUrl());
        	travel.setSource(ss.getSource());
        	travel.setPrice(ss.getPrice());
        	travel.setUrlString(ss.getUrlString());
        	travel.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		travelDao.saveAll(data);
                data.clear();
            }
        	data.add(travel);
        }
        if(!data.isEmpty()) {
        	travelDao.saveAll(data);
        }
        
        
        
        mav.setViewName("/admin/page/travel/travel_manage");
        return mav;
    }

    /**
     * /houtai/travel/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<TravelType> list = travelTypeDao.findAll(pageable);
        List<TravelType> travelTypeList = list.getContent(); //拿到list集合
        mav.addObject("travelTypeList", travelTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/travel/add");
        mav.setViewName("/admin/page/travel/add_update");
        return mav;
    }


    /**
     * /houtai/travel/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<TravelType> list = travelTypeDao.findAll(pageable);
        List<TravelType> travelTypeList = list.getContent();//拿到list集合
        mav.addObject("travelTypeList", travelTypeList);


        Travel travel = travelDao.findId(id);
        mav.addObject("travel", travel);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/travel/update?id=" + id);
        mav.setViewName("/admin/page/travel/add_update");
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
		List<Travel> userList =travelDao.TravelOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<TravelType> list = travelTypeDao.findAll(pageable);
		List<TravelType> TravelTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", TravelTypeList);
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
		Integer message_type_id=2;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","travel");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
	
    
    
    
}
