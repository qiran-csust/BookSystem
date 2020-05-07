package com.java456.controller.houtai;

import com.java456.dao.BankTypeDao;
import com.java456.entity.BankType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/houtai/bank/type")
public class HouTai_BankType_Controller {
    @Resource
    private BankTypeDao bankTypeDao;

    /**
     * /houtai/bank/type/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "银行类型管理");
        mav.setViewName("/admin/page/banktype/banktype_manage");
        return mav;
    }

    /**
     * /houtai/bank/type/add
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/bank/type/add");
        mav.setViewName("/admin/page/banktype/add_update");
        return mav;
    }


    /**
     * /houtai/bank/type/edit?id=1
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id) throws Exception {
        ModelAndView mav = new ModelAndView();

        BankType bankType = bankTypeDao.findId(id);
        mav.addObject("bankType", bankType);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/bank/type/update?id=" + id);
        mav.setViewName("/admin/page/banktype/add_update");
        return mav;
    }
}
