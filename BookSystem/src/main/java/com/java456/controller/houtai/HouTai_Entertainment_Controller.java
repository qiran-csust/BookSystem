package com.java456.controller.houtai;

import com.java456.dao.EntertainmentDao;
import com.java456.dao.EntertainmentTypeDao;
import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.entity.Bank;
import com.java456.entity.Entertainment;
import com.java456.entity.EntertainmentType;
import com.java456.entity.Food;
import com.java456.entity.FoodType;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.service.EntertainmentService;
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
@RequestMapping("/houtai/entertainment")
public class HouTai_Entertainment_Controller {

    @Resource
    private EntertainmentDao entertainmentDao;
    @Resource
    private EntertainmentService entertainmentService;
    @Resource
    private EntertainmentTypeDao entertainmentTypeDao;
    @Resource
    private MessageTypeDao messageTypeDao;
    @Resource
    private MessageDao messageDao;

    /**
     * /houtai/entertainment/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "娱乐管理");
        List<Message> messages = messageDao.findAllEntertainm();
        entertainmentDao.deleteAll();
        List<Entertainment> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Entertainment entertainment =new Entertainment();
        	entertainment.setId(ss.getId());
        	entertainment.setId(ss.getOrderNo());
        	entertainment.setAddrString(ss.getAddrString());
        	entertainment.setCreateDateTime(new Date());
        	entertainment.setImageUrl(ss.getImageUrl());
        	entertainment.setSource(ss.getSource());
        	entertainment.setPrice(ss.getPrice());
        	entertainment.setUrlString(ss.getUrlString());
        	entertainment.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		entertainmentDao.saveAll(data);
                data.clear();
            }
        	data.add(entertainment);
        }
        if(!data.isEmpty()) {
        	entertainmentDao.saveAll(data);
        }
        
        
        
        
        
        mav.setViewName("/admin/page/entertainment/entertainment_manage");
        return mav;
    }

    /**
     * /houtai/entertainment/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
        List<EntertainmentType> entertainmentTypeList = list.getContent(); //拿到list集合
        mav.addObject("entertainmentTypeList", entertainmentTypeList);


        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/entertainment/add");
        mav.setViewName("/admin/page/entertainment/add_update");
        return mav;
    }


    /**
     * /houtai/entertainment/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
        List<EntertainmentType> entertainmentTypeList = list.getContent();//拿到list集合
        mav.addObject("entertainmentTypeList", entertainmentTypeList);


        Entertainment entertainment = entertainmentDao.findId(id);
        mav.addObject("entertainment", entertainment);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/entertainment/update?id=" + id);
        mav.setViewName("/admin/page/entertainment/add_update");
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
		List<Entertainment> userList =entertainmentDao.EntertainmentOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
		List<EntertainmentType> EntertainmentTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", EntertainmentTypeList);
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
		Integer message_type_id=5;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","entertainment");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
    
    
}
