package com.java456.controller.houtai;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/houtai/great/info")
public class HouTai_GreatInfo_Controller {

    @GetMapping(value = "greatInfo")
    public ModelAndView greatInfo() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/admin/greatInfo");

        return mav;
    }
}

