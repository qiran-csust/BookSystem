package com.java456.dao;

import com.java456.entity.Bank;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BankDao extends JpaRepository<Bank, Integer>, JpaSpecificationExecutor<Bank> {
    @Query(value = "select * from t_bank where id = ?1", nativeQuery = true)
    public Bank findId(Integer id);
	@Query(value="select * from t_bank order by price ",nativeQuery = true)
	public List<Bank> BankOrderByPrice();
}
