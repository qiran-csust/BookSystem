package com.java456.controller.admin;

import com.java456.entity.GreatInfo;
import com.java456.entity.User;
import com.java456.service.GreatInfoService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping(value = "/admin/great/info")
public class Admin_GreatInfo_Controller {

    @Resource
    private GreatInfoService greatInfoService;

    /**
     *
     * /admin/great/info/addGreat
     * 收藏优惠券信息
     * @param couponsId
     */
    @PostMapping(value = "/addGreat")
    public JSONObject addGreat(Integer couponsId){
        JSONObject result = new JSONObject();

        // 根据用户id和typeID获取关注信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        if(couponsId != null){
            GreatInfo greatInfo = new GreatInfo();
            greatInfo.setCouponsId(couponsId);
            greatInfo.setUserId(user.getId());
            greatInfo.setCreateTime(new Date());

            // 将数据保存到数据库中
            GreatInfo save = greatInfoService.addGreatInfo(greatInfo);
            if(save != null){
                result.put("success", true);
                result.put("couponsId", save.getId());
                return result;
            }
        }
        // 其余情况都是收藏失败
        result.put("success", false);
          return result;
    }

    /**
     * /admin/great/info/cancelGreat
     * 取消收藏的优惠信息
     * @param greatId
     */
    @PostMapping(value = "/cancelGreat")
    public JSONObject cancelGreat(Integer greatId){
        JSONObject result = new JSONObject();
        GreatInfo greatInfo = greatInfoService.getGreatInfoById(greatId);
        if(greatInfo != null){
            Integer count = greatInfoService.deleteGreatInfo(greatInfo.getId());
            if(count > 0){
                // 取消收藏成功
                result.put("success", true);
                return result;
            }
        }
        // 其他情况表示取消收藏失败
        result.put("success", false);
        return result;
    }

}
