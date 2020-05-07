package com.java456.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.java456.dao.BookDao;
import com.java456.dao.FoodDao;
import com.java456.entity.Food;

@Service("foodService")
public class FoodServiceImpl implements FoodService {
	
	@Resource
	private FoodDao foodDao;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public Food repalce(Food curr,Food origin){
		
		if(curr.getAddrString()==null){
			curr.setAddrString(origin.getAddrString());
		}
		if(curr.getImageUrl()==null){
			curr.setImageUrl(origin.getImageUrl());
		}
		if(curr.getPrice()==null){
			curr.setPrice(origin.getPrice());
		}
		if(curr.getSource()==null){
			curr.setSource(origin.getSource());
		}
		
		
		if(curr.getUrlString()==null){
			curr.setUrlString(origin.getUrlString());
		}
		if(curr.getOrderNo()==null){
			curr.setOrderNo(origin.getOrderNo());
		}
		if(curr.getCreateDateTime()==null){
			curr.setCreateDateTime(origin.getCreateDateTime());
		}
		if(curr.getUpdateDateTime()==null){
			curr.setUpdateDateTime(origin.getUpdateDateTime());
		}
		if(curr.getUrlString() ==null){
			curr.setUrlString(origin.getUrlString());
		}
		if(curr.getFoodType()==null){
			curr.setFoodType(origin.getFoodType());
		}
		
		return curr;
	}
	
	
	
	@Override
	public void update(Food food) {
		Food origin = foodDao.findId(food.getId());
		//把没有值的数据  换成原数据库的数据。
		food = repalce(food, origin);
		//把没有值的数据  换成原数据库的数据。
		
		foodDao.save(food);
	}
	
	@Override
	public List<Food> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
		Page<Food> pages = foodDao.findAll(new Specification<Food>() {
			
			@Override
			public Predicate toPredicate(Root<Food> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				
				// 加入 等于 
				if (map.get("foodType") != null) {
					predicate.getExpressions().add(cb.equal(root.get("foodType"), map.get("foodType")));
				}
				
				return predicate;
			}
		}, pageable);
		return pages.getContent();
	}
	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long count=foodDao.count(new Specification<Food>() {
			@Override
			public Predicate toPredicate(Root<Food> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
				// 加入 等于 
				if (map.get("foodType") != null) {
					predicate.getExpressions().add(cb.equal(root.get("foodType"), map.get("foodType")));
				}
				
				return predicate;
			}
		});
		return count;
	}
}

