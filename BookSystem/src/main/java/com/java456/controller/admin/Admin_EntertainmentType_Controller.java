package com.java456.controller.admin;

import com.java456.dao.EntertainmentTypeDao;
import com.java456.entity.EntertainmentType;
import com.java456.service.EntertainmentTypeService;
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
@RequestMapping("/admin/entertainment/type")
public class Admin_EntertainmentType_Controller {
    @Resource
    private EntertainmentTypeDao entertainmentTypeDao;
    @Resource
    private EntertainmentTypeService entertainmentTypeService;

    /**
     *   /admin/entertainment/type/add
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid EntertainmentType entertainmentType, BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            entertainmentType.setCreateDateTime(new Date());
            entertainmentTypeDao.save(entertainmentType);
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }




    /**
     *  /admin/entertainment/type/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid EntertainmentType entertainmentType, BindingResult bindingResult,
                             HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();

        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            entertainmentType.setUpdateDateTime(new Date());
            entertainmentTypeService.update(entertainmentType);
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }



    /**
     * /admin/entertainment/type/list
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
        Page<EntertainmentType> list = entertainmentTypeDao.findAll(pageable);
        List<EntertainmentType> foodTypeList = list.getContent();//拿到list集合
        long total = entertainmentTypeDao.count();
        map.put("data", foodTypeList);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     * /admin/entertainment/type/delete
     */
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            entertainmentTypeDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }
}
