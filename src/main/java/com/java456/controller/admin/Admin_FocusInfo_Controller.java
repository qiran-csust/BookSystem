package com.java456.controller.admin;
import com.java456.entity.FocusInfo;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.User;
import com.java456.service.FocusInfoService;
import com.java456.service.MessageService;
import com.java456.service.MessageTypeService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠类型关注的controller
 */
@RestController
@RequestMapping(value = "/admin/focus/info")
public class Admin_FocusInfo_Controller {

    @Resource
    private MessageTypeService messageTypeService;
    @Resource
    private FocusInfoService focusInfoService;
    @Resource
    private MessageService messageService;

    /**
     * 判断指定类型的优惠信息是否被当前用户关注
     * @param typeName 类型的名称，即message_type的name字段
     * @return
     */
    @PostMapping(value = "/checkFocus")
    public JSONObject checkFocus(String typeName){
        JSONObject result = new JSONObject();

        // 通过messageType获取typeId
        MessageType messageType = messageTypeService.getMessageTypeByName(typeName);

        // 根据用户id和typeID获取关注信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");

        FocusInfo focusInfo = focusInfoService.getFocusInfoByUserIdTypeId(user.getId(), messageType.getId());

        if(null == focusInfo){
            result.put("res", false);
        }else{
            result.put("res", true);
            result.put("focusId", focusInfo.getId());
        }
        return result;
    }

    /**
     * /admin/focus/info/addFocus
     * 添加关注
     * @param typeName
     * @return
     */
    @PostMapping(value = "/addFocus")
    public JSONObject addFocus(String typeName){
        JSONObject result = new JSONObject();

        MessageType messageType = messageTypeService.getMessageTypeByName(typeName);
        // 根据用户id和typeID获取关注信息
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        FocusInfo focusInfo = new FocusInfo();
        focusInfo.setUserId(user.getId());
        focusInfo.setMessageType(messageType);
        focusInfo.setCreateTime(new Date());

        // 将关注信息添加到数据库中
        FocusInfo save = focusInfoService.addFocusInfo(focusInfo);
        if(save == null){
            result.put("success", false);
        }else{
            result.put("success", true);
            result.put("focusId", save.getId());
        }
        return result;
    }

    /**
     * /admin/focus/info/cancelFocus
     * 取消关注
     * @param focusId 关注信息的id字段
     */
    @PostMapping(value = "/cancelFocus")
    public JSONObject cancelFocus(Integer focusId){
        JSONObject result = new JSONObject();
        FocusInfo focusInfo = focusInfoService.getFocusInfoById(focusId);
        if(focusInfo != null){
            Integer integer = focusInfoService.deleteFocusInfo(focusInfo);
            if(integer > 0){
                // 取关成功
                result.put("success", true);
            }else{
                // 取关失败
                result.put("success", false);
            }
        }else{
            // 数据库没有记录，返回取关成功
            result.put("success", true);
        }


        return result;
    }

    /**
     * 获取指定类型最新的优惠信息
     *  /admin/focus/info/list
     * @param typeId 类型的id
     */
    @RequestMapping(value = "/list")
    public Map<String, Object> list(Integer typeId){
        Map<String, Object> map = new HashMap<>();
        List<Message> messageList = messageService.selectNewMessage(typeId, 10);
        map.put("messageList", messageList);
        return map;
    }
}
