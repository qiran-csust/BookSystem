package com.java456.controller.admin;


import com.java456.dao.MoviesTypeDao;
import com.java456.entity.MoviesType;
import com.java456.service.MoviesTypeService;
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
@RequestMapping("/admin/movies/type")
public class Admin_MoviesType_Controller {

    @Resource
    private MoviesTypeDao moviesTypeDao;
    @Resource
    private MoviesTypeService moviesTypeService;

    /**
     *   /admin/movies/type/add
     */
    @RequestMapping("/add")
    public JSONObject add(@Valid MoviesType moviesType, BindingResult bindingResult,
                          HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            moviesType.setCreateDateTime(new Date());
            moviesTypeDao.save(moviesType);
            result.put("success", true);
            result.put("msg", "添加成功");
        }
        return result;
    }




    /**
     *  /admin/movies/type/update
     */
    @RequestMapping("/update")
    public JSONObject update(@Valid MoviesType moviesType, BindingResult bindingResult,
                             HttpServletRequest request)throws Exception {
        JSONObject result = new JSONObject();

        if(bindingResult.hasErrors()){
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
        }else{
            moviesType.setUpdateDateTime(new Date());
            moviesTypeService.update(moviesType);
            result.put("success", true);
            result.put("msg", "修改成功");
        }
        return result;
    }



    /**
     * /admin/movies/type/list
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
        Page<MoviesType> list = moviesTypeDao.findAll(pageable);
        List<MoviesType> foodTypeList = list.getContent();//拿到list集合
        long total = moviesTypeDao.count();
        map.put("data", foodTypeList);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     * /admin/movies/type/delete
     */
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            moviesTypeDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }
}
