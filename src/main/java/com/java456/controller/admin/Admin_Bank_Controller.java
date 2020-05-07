package com.java456.controller.admin;

import com.java456.dao.BankDao;
import com.java456.dao.MessageDao;
import com.java456.entity.Bank;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.service.BankService;
import com.java456.service.MessageService;

import net.sf.json.JSONObject;

import org.hibernate.type.NTextType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/bank")
public class Admin_Bank_Controller {

    @Resource
    private BankDao bankDao;
    @Resource
    private BankService bankService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;
    /**
     * /admin/bank/add
     * @param bank
     * @param bindingResult
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public JSONObject add(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
    		@Valid Bank bank,@Valid Message message, BindingResult bindingResult, HttpServletResponse response,
                          HttpServletRequest request){
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
        	Map<String, Object> map = new HashMap<>();
            long total = messageService.getTotal(map);
            int number=(int)total;
            bank.setId(number+1);
            bank.setCreateDateTime(new Date());
            bankDao.save(bank);
            message.setId(bank.getId());
            message.setOrderNo(bank.getOrderNo());
            message.setAddrString(bank.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(bank.getImageUrl());
            message.setSource(bank.getSource());
            message.setPrice(bank.getPrice());
            message.setUrlString(bank.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(4);      
            message.setMessageType(messageType);   
            messageDao.save(message);
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }

    /**
     * /admin/bank/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid Bank bank,@Valid Message message,  BindingResult bindingResult, HttpServletResponse response,
                             HttpServletRequest request){
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            bank.setUpdateDateTime(new Date());
            bankService.update(bank);
            message.setUpdateDateTime(new Date());
            message.setOrderNo(bank.getOrderNo());
            message.setAddrString(bank.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(bank.getImageUrl());
            message.setSource(bank.getSource());
            message.setPrice(bank.getPrice());
            message.setUrlString(bank.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(4);
            message.setMessageType(messageType);
            messageService.update(message);
            result.put("success", true);
            result.put("msg", "更新成功");
        }
        return result;
    }

    /**
     * /admin/bank/list
     * @param page  默认 1
     * @param limit 每页数据数
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        List<Bank> list = bankService.list(map, page-1, limit);
        long total = bankService.getTotal(map);

        map.put("data", list);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     *  /admin/bank/delete
     * @param idsStr id组成的字符串，以逗号分隔
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String idsStr, HttpServletResponse response,
                             HttpServletRequest request){
        String[] ids = idsStr.split(",");
        JSONObject result = new JSONObject();
        
        for (int i = 0; i < ids.length; i++) {
            bankDao.deleteById(Integer.parseInt(ids[i])); 
            messageDao.deleteById(Integer.parseInt(ids[i])); 
        }
        result.put("success", true);
        return result;
    }
































}
