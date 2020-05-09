package com.java456.controller.admin;

import com.java456.dao.MessageDao;
import com.java456.dao.MoviesDao;
import com.java456.dao.UserHistoryDao;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.Movies;
import com.java456.entity.User;
import com.java456.entity.UserHistory;
import com.java456.service.MessageService;
import com.java456.service.MoviesService;
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
@RequestMapping("/admin/movies")
public class Admin_Movies_Controller {

    @Resource
    private MoviesDao moviesDao;
    @Resource
    private MoviesService moviesService;
    @Resource
    private MessageDao messageDao;
    @Resource
    private MessageService messageService;
    @Resource
    private UserHistoryDao userHistoryDao;
    /**
     * /admin/movies/add
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid Movies movies,@Valid Message message, BindingResult bindingResult, HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();

        if (bindingResult.hasErrors()) {
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
        	Map<String, Object> map = new HashMap<>();
            long total = messageService.getTotal(map);
            int number=(int)total;
            movies.setId(number+1);
            movies.setCreateDateTime(new Date());
            moviesDao.save(movies);
            message.setId(movies.getId());
            message.setOrderNo(movies.getOrderNo());
            message.setAddrString(movies.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(movies.getImageUrl());
            message.setSource(movies.getSource());
            message.setPrice(movies.getPrice());
            message.setUrlString(movies.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(1);
            
            message.setMessageType(messageType);
    
            messageDao.save(message);
            
        
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }


    /**
     *  /admin/movies/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid Movies movies,@Valid Message message, @Valid UserHistory userHistory,
    		BindingResult bindingResult, HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            movies.setUpdateDateTime(new Date());
            moviesService.update(movies);
			message.setUpdateDateTime(new Date());
            message.setOrderNo(movies.getOrderNo());
            message.setAddrString(movies.getAddrString());
            message.setCreateDateTime(new Date());
            message.setImageUrl(movies.getImageUrl());
            message.setSource(movies.getSource());
            message.setPrice(movies.getPrice());
            message.setUrlString(movies.getUrlString());
            MessageType messageType = new MessageType();
            messageType.setId(1);   
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
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }


    /**
     * /admin/movies/list
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
        List<Movies> list = moviesService.list(map, page-1, limit);
        long total = moviesService.getTotal(map);

        map.put("data", list);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }


    /**
     * /admin/movies/delete
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            moviesDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }

}
