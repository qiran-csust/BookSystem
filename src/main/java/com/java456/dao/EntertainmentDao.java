package com.java456.dao;

import com.java456.entity.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EntertainmentDao extends JpaRepository<Entertainment, Integer>, JpaSpecificationExecutor<Entertainment> {
    @Query(value = "select * from t_entertainment where id = ?1", nativeQuery = true)
    public Entertainment findId(Integer id);
}
