package com.java456.dao;

import com.java456.entity.FocusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FocusInfoDao extends JpaRepository<FocusInfo, Integer>, JpaSpecificationExecutor<FocusInfo> {

    @Query(value = "select * from t_focus_info where id = ?1", nativeQuery = true)
    FocusInfo getFocusInfoById(Integer id);

    @Query(value = "select * from t_focus_info where user_id = ?1 and type_id =?2", nativeQuery = true)
    FocusInfo getFocusInfoByUserIdTypeId(Integer userId, Integer typeId);

    @Query(value = "select * from t_focus_info where user_id = ?1", nativeQuery = true)
    List<FocusInfo> getFocusInfosByUserId(Integer userId);
}
