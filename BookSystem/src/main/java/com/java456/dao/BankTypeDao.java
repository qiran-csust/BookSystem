package com.java456.dao;

import com.java456.entity.BankType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BankTypeDao extends JpaRepository<BankType, Integer>, JpaSpecificationExecutor<BankType> {
    @Query(value = "select * from t_a_bank_type where id = ?1", nativeQuery = true)
    public BankType findId(Integer id);
}
