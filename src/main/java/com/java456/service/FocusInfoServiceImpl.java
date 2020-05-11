package com.java456.service;

import com.java456.dao.FocusInfoDao;
import com.java456.entity.FocusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class FocusInfoServiceImpl implements FocusInfoService {

    @Resource
    private FocusInfoDao focusInfoDao;


    @Override
    public FocusInfo getFocusInfoById(Integer id) {
        FocusInfo focusInfo = focusInfoDao.getFocusInfoById(id);
        return focusInfo;
    }

    @Override
    public FocusInfo getFocusInfoByUserIdTypeId(Integer userId, Integer typeId) {
        FocusInfo focusInfo = focusInfoDao.getFocusInfoByUserIdTypeId(userId, typeId);
        return focusInfo;
    }

    @Override
    public List<FocusInfo> getFocusInfosByUserId(Integer userId) {
        List<FocusInfo> focusInfoList = focusInfoDao.getFocusInfosByUserId(userId);
        return focusInfoList;
    }

    @Override
    public FocusInfo addFocusInfo(FocusInfo focusInfo) {
        FocusInfo save = focusInfoDao.save(focusInfo);
        return save;
    }

    @Override
    public Integer deleteFocusInfo(FocusInfo focusInfo) {
        int count = 0;
        try{
            focusInfoDao.delete(focusInfo);
            count =1;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return count;
    }
}
