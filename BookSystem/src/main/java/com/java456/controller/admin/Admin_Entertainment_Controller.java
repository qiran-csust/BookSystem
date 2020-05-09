package com.java456.controller.admin;

import com.java456.dao.EntertainmentDao;
import com.java456.dao.MessageDao;
import com.java456.dao.UserHistoryDao;
import com.java456.entity.Entertainment;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.User;
import com.java456.entity.UserHistory;
import com.java456.service.EntertainmentService;
import com.java456.service.MessageService;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
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
@RequestMapping("/admin/entertainment")
public class Admin_Entertainment_Controller {

    @Resource
    private EntertainmentDao enterDao;
    @Resource
    private EntertainmentService enterService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;
    @Resource
    private UserHistoryDao userHistoryDao;
    /**
     *  /admin/entertainment/add
     * @param entertainment
     * @param bindingResult
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid Entertainment entertainment,@Valid Message message, BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request){
        JSONObject res = new JSONObject();
        if(bindingResult.hasErrors()){
            res.put("successs", false);
            res.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else {
        	Map<String, Object> map = new HashMap<>();
            long total = messageService.getTotal(map);
            int number=(int)total;
            entertainment.setId(number+1);
            entertainment.setCreateDateTime(new Date());
            enterDao.save(entertainment);
            message.setId(entertainment.getId());
            message.setOrderNo(entertainment.getOrderNo());
            message.setAddrString(entertainment.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(entertainment.getImageUrl());
            message.setSource(entertainment.getSource());
            message.setPrice(entertainment.getPrice());
            message.setUrlString(entertainment.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(5);
            
            message.setMessageType(messageType);
            messageDao.save(message);
            
            
            
            
            res.put("success", true);
            res.put("msg", "添加成功");
        }
        return res;
    }

    /**
     *  /admin/entertainment/update
     * @param entertainment
     * @param bindingResult
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid Entertainment entertainment,@Valid Message message,  
    		@Valid UserHistory userHistory,BindingResult bindingResult,
                             HttpServletResponse response,
                             HttpServletRequest request){
        JSONObject res = new JSONObject();
        if(bindingResult.hasErrors()){
            res.put("success", false);
            res.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            entertainment.setUpdateDateTime(new Date());
            enterService.update(entertainment);
            message.setUpdateDateTime(new Date());
            message.setOrderNo(entertainment.getOrderNo());
            message.setId(message.getId());
            message.setAddrString(entertainment.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(entertainment.getImageUrl());
            message.setSource(entertainment.getSource());
            message.setPrice(entertainment.getPrice());
            message.setUrlString(entertainment.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(5);
            message.setMessageType(messageType);
            messageService.update(message);
            
            List<Integer> list = userHistoryDao.findUserHistories();
            Integer count=0;
            int time=1;
            for(Integer integer:list) 
            {
            	if (time<message.getMessageType().getId()&&integer!=null) {
            		time++;
            		count=integer+count;
            	}
            }
            System.out.print(count);
            User user =(User)SecurityUtils.getSubject().getSession().getAttribute("currentUser");
            userHistory.setId(count+message.getOrderNo());
            userHistory.setUserId(user.getId());
            userHistory.setSkimDateTime(new Date());
            userHistory.setMessage(message);
            userHistoryDao.save(userHistory);  
            
            res.put("success", true);
            res.put("msg", "修改成功");
        }
        return res;
    }

    /**
     * /admin/entertainment/list
     * @param page    默认1
     * @param limit   数据多少
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Entertainment> list = enterService.list(map, page-1, limit);
        long total = enterService.getTotal(map);

        map.put("data", list);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     * /admin/entertainment/delete
     */
    //for (Entertainment entertainment:list)
    //	System.out.print(entertainment.getId());
    //System.out.print(total);
    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, 
    		@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
    		HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            enterDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }

}
