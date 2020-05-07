package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.FoodType;

public interface FoodTypeDao extends JpaRepository<FoodType,Integer>,JpaSpecificationExecutor< FoodType>  {
	
	
	@Query(value="select * from t_a_food_type where id = ?1",nativeQuery = true)
	public FoodType findId(Integer id);
}
