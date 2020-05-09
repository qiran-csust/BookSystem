package com.java456.service;

import com.java456.dao.GreatInfoDao;
import com.java456.entity.GreatInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GreatInfoServiceImpl implements GreatInfoService {

    @Resource
    private GreatInfoDao greatInfoDao;

    @Override
    public GreatInfo addGreatInfo(GreatInfo greatInfo) {
        GreatInfo save = greatInfoDao.save(greatInfo);
        return save;
    }

    @Override
    public Integer deleteGreatInfo(Integer id) {
        int count = 0;
        try{
            greatInfoDao.deleteById(id);
            count = 1;
        }catch (EmptyResultDataAccessException ex){
            ex.getMessage();
        }
        return count;
    }

    @Override
    public GreatInfo getGreatInfoById(Integer id) {
        GreatInfo greatInfo = greatInfoDao.getGreatInfoById(id);
        return greatInfo;
    }

    @Override
    public GreatInfo selectByCouponIdUserId(Integer couponId, Integer userId) {
        return greatInfoDao.selectByCouponIdUserId(couponId, userId);
    }

    // 获取收藏优惠信息的总数
    @Override
    public long getTotalNumberByUserId(Integer userId) {
        long count = greatInfoDao.getTotalNumberByUserId(userId);
        return count;
    }
}
