package com.java456.controller.houtai;

import com.java456.dao.MoviesTypeDao;
import com.java456.entity.MoviesType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/houtai/movies/type")
public class HouTai_MoviesType_Controller {

    @Resource
    private MoviesTypeDao moviesTypeDao;

    /**
     * /houtai/movies/type/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "电影类型管理");
        mav.setViewName("/admin/page/moviestype/moviestype_manage");
        return mav;
    }

    /**
     * /houtai/movies/type/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/movies/type/add");
        mav.setViewName("/admin/page/moviestype/add_update");
        return mav;
    }


    /**
     * /houtai/movies/type/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        MoviesType moviesType = moviesTypeDao.findId(id);
        mav.addObject("moviesType", moviesType);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/movies/type/update?id=" + id);
        mav.setViewName("/admin/page/moviestype/add_update");
        return mav;
    }
}
