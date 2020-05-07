package com.java456.dao;

import com.java456.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TravelDao extends JpaRepository<Travel, Integer>, JpaSpecificationExecutor<Travel> {
    @Query(value = "select * from t_travel where id = ?1", nativeQuery = true)
    public Travel findId(Integer id);
}
