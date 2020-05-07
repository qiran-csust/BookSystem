package com.java456.controller.houtai;

import com.java456.dao.MessageDao;
import com.java456.dao.TravelDao;
import com.java456.dao.TravelTypeDao;
import com.java456.entity.Food;
import com.java456.entity.Message;
import com.java456.entity.Travel;
import com.java456.entity.TravelType;
import com.java456.service.TravelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

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
        	travel.setOrderNo(ss.getId());
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
}
