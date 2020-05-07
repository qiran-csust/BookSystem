package com.java456.dao;

import com.java456.entity.TrafficType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TrafficTypeDao extends JpaRepository<TrafficType, Integer>, JpaSpecificationExecutor<TrafficType> {
    @Query(value = "select * from t_a_traffic_type where id = ?1", nativeQuery = true)
    public TrafficType findId(Integer id);
}
