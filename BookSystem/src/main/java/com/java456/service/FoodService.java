package com.java456.service;

import java.util.List;
import java.util.Map;
import com.java456.entity.Food;

public interface FoodService {
	public void update(Food  food );
	
	/**
	 * @param map
	 * @param page  从0开始 
	 * @param pageSize
	 */
	public List<Food> list(Map<String,Object> map,Integer page,Integer pageSize);
	
	public Long getTotal(Map<String,Object> map);
}
