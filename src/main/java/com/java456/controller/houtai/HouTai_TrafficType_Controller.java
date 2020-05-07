package com.java456.controller.houtai;

import com.java456.dao.TrafficTypeDao;
import com.java456.entity.TrafficType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/houtai/traffic/type")
public class HouTai_TrafficType_Controller {
    @Resource
    private TrafficTypeDao trafficTypeDao;

    /**
     * /houtai/traffic/type/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "交通类型管理");
        mav.setViewName("/admin/page/traffictype/traffictype_manage");
        return mav;
    }

    /**
     * /houtai/traffic/type/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/traffic/type/add");
        mav.setViewName("/admin/page/traffictype/add_update");
        return mav;
    }


    /**
     * /houtai/traffic/type/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        TrafficType trafficType = trafficTypeDao.findId(id);
        mav.addObject("trafficType", trafficType);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/traffic/type/update?id=" + id);
        mav.setViewName("/admin/page/traffictype/add_update");
        return mav;
    }
}
