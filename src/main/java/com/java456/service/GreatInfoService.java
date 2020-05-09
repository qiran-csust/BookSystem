package com.java456.service;

import com.java456.entity.GreatInfo;

public interface GreatInfoService {
    GreatInfo addGreatInfo(GreatInfo greatInfo);

    Integer deleteGreatInfo(Integer id);

    GreatInfo getGreatInfoById(Integer id);

    GreatInfo selectByCouponIdUserId(Integer couponId, Integer userId);

    long getTotalNumberByUserId(Integer userId);
}
