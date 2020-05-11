package com.java456.controller.admin;

import com.java456.entity.GreatInfo;
import com.java456.entity.Message;
import com.java456.entity.User;
import com.java456.service.GreatInfoService;
import com.java456.service.MessageService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/great/info")
public class Admin_GreatInfo_Controller {

    @Resource
    private GreatInfoService greatInfoService;
    @Resource
    private MessageService messageService;

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
            // 判断是否已经收藏
            GreatInfo greatInfo = greatInfoService.selectByCouponIdUserId(couponsId, user.getId());
            if(greatInfo == null){
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
            }else{
                // 已经收藏过了，直接返回收藏成功
                result.put("success", true);
                result.put("couponsId", greatInfo.getId());
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
     * @param couponsId
     */
    @PostMapping(value = "/cancelGreat")
    @Transactional
    public JSONObject cancelGreat(Integer couponsId){
        JSONObject result = new JSONObject();
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");

        GreatInfo greatInfo = greatInfoService.selectByCouponIdUserId(couponsId, user.getId());
        if(greatInfo == null){
            Integer count = greatInfoService.deleteGreatInfoById(couponsId, user.getId());
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

    /**
     * /admin/great/info/list
     * 获取收藏的优惠信息
     */
    @RequestMapping(value = "/list")
    public Map<String, Object> list(Integer page, Integer limit){
        Map<String, Object> map = new HashMap<>();
        // 根据用户id和typeID获取关注信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        List<Message> messageList = messageService.selectGreatMessageByPaged(user.getId(), page, limit);
        long total = greatInfoService.getTotalNumberByUserId(user.getId());
        map.put("data", messageList);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }
}
