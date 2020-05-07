package com.java456.service;

import com.java456.entity.FocusInfo;

import java.util.List;

public interface FocusInfoService {

    FocusInfo getFocusInfoById(Integer id);

    /**
     * 根据用户ID和类型ID获取关注信息
     * @param userId
     * @param typeId
     */
    FocusInfo getFocusInfoByUserIdTypeId(Integer userId, Integer typeId);

    /**
     * 根据用户ID，获取该用户关注的类型信息
     * @param userId
     */
    List<FocusInfo> getFocusInfosByUserId(Integer userId);

    FocusInfo addFocusInfo(FocusInfo focusInfo);

    Integer deleteFocusInfo(FocusInfo focusInfo);
}
