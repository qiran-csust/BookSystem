package com.java456.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.Food;

public interface FoodDao extends JpaRepository<Food,Integer>,JpaSpecificationExecutor< Food> {
	
	
	@Query(value="select * from t_food where id = ?1",nativeQuery = true)
	public Food  findId(Integer id);
	@Query(value="select * from t_food order by price ",nativeQuery = true)
	public List<Food> FoodOrderByPrice();
}
