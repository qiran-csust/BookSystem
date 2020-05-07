package com.java456.controller.admin;

import com.java456.dao.TrafficTypeDao;
import com.java456.entity.TrafficType;
import com.java456.service.TrafficTypeService;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/admin/traffic/type")
public class Admin_TrafficType_Controller {

    @Resource
    private TrafficTypeDao trafficTypeDao;
    @Resource
    private TrafficTypeService trafficTypeService;

    /**
     *   /admin/traffic/type/add
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid TrafficType trafficType  , BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            trafficType.setCreateDateTime(new Date());
            trafficTypeDao.save(trafficType);
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }




    /**
     *  /admin/traffic/type/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid TrafficType trafficType, BindingResult bindingResult,
                             HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();

        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            trafficType.setUpdateDateTime(new Date());
            trafficTypeService.update(trafficType);
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }



    /**
     * /admin/traffic/type/list
     * @param page    默认1
     * @param limit   数据多少
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Pageable pageable=new PageRequest(page-1,limit, Sort.Direction.ASC,"orderNo");
        Page<TrafficType> list = trafficTypeDao.findAll(pageable);
        List<TrafficType> foodTypeList = list.getContent();//拿到list集合
        long total = trafficTypeDao.count();
        map.put("data", foodTypeList);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     * /admin/traffic/type/delete
     */
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            trafficTypeDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }
}
