package com.java456.controller.houtai;

import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.dao.TrafficDao;
import com.java456.dao.TrafficTypeDao;
import com.java456.entity.Bank;
import com.java456.entity.BankType;
import com.java456.entity.Food;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Traffic;
import com.java456.entity.TrafficType;
import com.java456.service.TrafficService;
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
@RequestMapping("/houtai/traffic")
public class HouTai_Traffic_Controller {

    @Resource
    private TrafficDao trafficDao;
    @Resource
    private TrafficService trafficService;
    @Resource
    private TrafficTypeDao trafficTypeDao;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageTypeDao messageTypeDao;
    /**
     * /houtai/traffic/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "交通管理");
        List<Message> messages = messageDao.findAllTraffic();
        trafficDao.deleteAll();
        List<Traffic> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Traffic traffic =new Traffic();
        	traffic.setId(ss.getId());
        	traffic.setAddrString(ss.getAddrString());
        	traffic.setCreateDateTime(new Date());
        	traffic.setImageUrl(ss.getImageUrl());
        	traffic.setSource(ss.getSource());
        	traffic.setPrice(ss.getPrice());
        	traffic.setUrlString(ss.getUrlString());
        	traffic.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		trafficDao.saveAll(data);
                data.clear();
            }
        	data.add(traffic);
        }
        if(!data.isEmpty()) {
        	trafficDao.saveAll(data);
        }
        
        mav.setViewName("/admin/page/traffic/traffic_manage");
        return mav;
    }

    /**
     * /houtai/traffic/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<TrafficType> list = trafficTypeDao.findAll(pageable);
        List<TrafficType> trafficTypeList = list.getContent(); //拿到list集合
        mav.addObject("trafficTypeList", trafficTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/traffic/add");
        mav.setViewName("/admin/page/traffic/add_update");
        return mav;
    }


    /**
     * /houtai/traffic/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<TrafficType> list = trafficTypeDao.findAll(pageable);
        List<TrafficType> trafficTypeList = list.getContent();//拿到list集合
        mav.addObject("trafficTypeList", trafficTypeList);


        Traffic traffic = trafficDao.findId(id);
        mav.addObject("traffic", traffic);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/traffic/update?id=" + id);
        mav.setViewName("/admin/page/traffic/add_update");
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
		List<Traffic> userList =trafficDao.TrafficOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<TrafficType> list = trafficTypeDao.findAll(pageable);
		List<TrafficType> TrafficTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", TrafficTypeList);
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
		Integer message_type_id=6;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","traffic");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
	
    
}
