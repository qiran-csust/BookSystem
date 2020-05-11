package com.java456.dao;

import com.java456.entity.GreatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GreatInfoDao extends JpaRepository<GreatInfo, Integer>, JpaSpecificationExecutor<GreatInfo> {
    @Query(value = "select * from t_great_info where id = ?1", nativeQuery = true)
    GreatInfo getGreatInfoById(Integer id);

    @Query(value = "select * from t_great_info where coupons_id = ?1 and user_id = ?2", nativeQuery = true)
    GreatInfo selectByCouponIdUserId(Integer couponId, Integer userId);

    @Query(value = "select count(id) from t_great_info where user_id =?1", nativeQuery = true)
    long getTotalNumberByUserId(Integer userId);

    @Query(value = "delete from t_great_info where coupons_id = ?1 and user_id = ?2", nativeQuery = true)
    @Modifying
    Integer deleteGreatInfoById(Integer conpouId, Integer userId);
}
