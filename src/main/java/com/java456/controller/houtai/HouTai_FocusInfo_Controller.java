package com.java456.controller.houtai;

import com.java456.entity.FocusInfo;
import com.java456.entity.User;
import com.java456.service.FocusInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/houtai/focus/info")
public class HouTai_FocusInfo_Controller {

    @Resource
    private FocusInfoService focusInfoService;

    @GetMapping(value = "focusInfo")
    public ModelAndView focusInfo(){
        ModelAndView mav = new ModelAndView();

        // 获取当前用户关注的类型信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        List<FocusInfo> focusInfoList = focusInfoService.getFocusInfosByUserId(user.getId());
        mav.addObject("focusInfoList", focusInfoList);
        mav.setViewName("/admin/focusInfo");
        return mav;
    }
}

