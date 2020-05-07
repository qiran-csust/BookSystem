package com.java456.controller.houtai;

import com.java456.dao.TravelTypeDao;
import com.java456.entity.TravelType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/houtai/travel/type")
public class HouTai_TravelType_Controller {
    @Resource
    private TravelTypeDao travelTypeDao;

    /**
     * /houtai/travel/type/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "旅游类型管理");
        mav.setViewName("/admin/page/traveltype/traveltype_manage");
        return mav;
    }

    /**
     * /houtai/travel/type/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/travel/type/add");
        mav.setViewName("/admin/page/traveltype/add_update");
        return mav;
    }


    /**
     * /houtai/travel/type/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        TravelType travelType = travelTypeDao.findId(id);
        mav.addObject("travelType", travelType);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/travel/type/update?id=" + id);
        mav.setViewName("/admin/page/traveltype/add_update");
        return mav;
    }
}
