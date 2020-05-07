package com.java456.controller.houtai;

import com.java456.dao.EntertainmentTypeDao;
import com.java456.entity.EntertainmentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/houtai/entertainment/type")
public class HouTai_EntertainmentType_Controller {
    @Resource
    private EntertainmentTypeDao entertainmentTypeDao;

    /**
     * /houtai/entertainment/type/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "娱乐类型管理");
        mav.setViewName("/admin/page/entertainmenttype/entertainmenttype_manage");
        return mav;
    }

    /**
     * /houtai/entertainment/type/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/entertainment/type/add");
        mav.setViewName("/admin/page/entertainmenttype/add_update");
        return mav;
    }


    /**
     * /houtai/entertainment/type/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        EntertainmentType entertainmentType = entertainmentTypeDao.findId(id);
        mav.addObject("entertainmentType", entertainmentType);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/entertainment/type/update?id=" + id);
        mav.setViewName("/admin/page/entertainmenttype/add_update");
        return mav;
    }
}
