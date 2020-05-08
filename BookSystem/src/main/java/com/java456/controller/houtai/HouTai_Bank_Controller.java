package com.java456.controller.houtai;

import com.java456.dao.BankDao;
import com.java456.dao.BankTypeDao;
import com.java456.dao.MessageDao;
import com.java456.dao.MessageTypeDao;
import com.java456.entity.Bank;
import com.java456.entity.BankType;
import com.java456.entity.Food;
import com.java456.entity.FoodType;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/houtai/bank")
public class HouTai_Bank_Controller {

    @Resource
    private BankDao bankDao;
    @Resource
    private BankService bankService;
    @Resource
    private BankTypeDao bankTypeDao;
    @Resource
    private MessageTypeDao messageTypeDao;
    @Resource
    private MessageDao messageDao;
    /**
     * /houtai/bank/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "银行管理");
        
        List<Message> messages = messageDao.findAllBank();
        bankDao.deleteAll();
        List<Bank> data = new ArrayList<>();
        for (Message ss:messages)
        {
        	Bank bank =new Bank();
        	bank.setId(ss.getId());
        	bank.setAddrString(ss.getAddrString());
        	bank.setCreateDateTime(new Date());
        	bank.setImageUrl(ss.getImageUrl());
        	bank.setSource(ss.getSource());
        	bank.setPrice(ss.getPrice());
        	bank.setUrlString(ss.getUrlString());
        	bank.setOrderNo(ss.getOrderNo());
        	if(data.size() == 300) {
        		bankDao.saveAll(data);
                data.clear();
            }
        	data.add(bank);
        }
        if(!data.isEmpty()) {
        	bankDao.saveAll(data);
        }

        mav.setViewName("/admin/page/bank/bank_manage");
        return mav;
    }

    /**
     * /houtai/bank/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<BankType> list = bankTypeDao.findAll(pageable);
        List<BankType> bankTypeList = list.getContent(); //拿到list集合
        mav.addObject("bankTypeList", bankTypeList);
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/bank/add");
        mav.setViewName("/admin/page/bank/add_update");
        return mav;
    }


    /**
     * /houtai/bank/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
        Page<BankType> list = bankTypeDao.findAll(pageable);
        List<BankType> bankTypeList = list.getContent();//拿到list集合
        mav.addObject("bankTypeList", bankTypeList);


        Bank bank = bankDao.findId(id);
        mav.addObject("bank", bank);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/bank/update?id=" + id);
        mav.setViewName("/admin/page/bank/add_update");
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
		List<Bank> userList =bankDao.BankOrderByPrice();
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<BankType> list = bankTypeDao.findAll(pageable);
		List<BankType> BankTypeList = list.getContent();//拿到list集合
		mav.addObject("TypeList", BankTypeList);
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
		Integer message_type_id=4;
		List<Message> userList =messageDao.selectMessages(message_type_id, source);
		Pageable pageable=new PageRequest(0,100, Sort.Direction.ASC,"orderNo");
		Page<MessageType> list = messageTypeDao.findAll(pageable);
		List<MessageType> MessageTypeList = list.getContent();//拿到list集合
		model.addAttribute("type","bank");
		mav.addObject("MessageTypeList", MessageTypeList);
		mav.addObject("userList", userList);
		mav.addObject("title", "选择查询");
		mav.setViewName("/admin/select");
		return mav;

	}
	
    
    
}
