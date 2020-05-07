package com.java456.dao;

import com.java456.entity.GreatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GreatInfoDao extends JpaRepository<GreatInfo, Integer>, JpaSpecificationExecutor<GreatInfo> {

}
