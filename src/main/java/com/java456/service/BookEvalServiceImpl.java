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

import com.java456.dao.BookEvalDao;
import com.java456.entity.BookEval;;

@Service("bookEvalService")
public class BookEvalServiceImpl implements BookEvalService{
	@Resource
	private BookEvalDao  bookEvalDao;
	
	
	public void update(BookEval bookEval) {
		BookEval origin = bookEvalDao.findId(bookEval.getId());
		//把没有值的数据  换成原数据库的数据。
		bookEval = repalce(bookEval, origin);
		//把没有值的数据  换成原数据库的数据。
		
		bookEvalDao.save(bookEval);
	}
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public BookEval repalce(BookEval curr,BookEval origin){
		if(curr.getEval()==null){
			curr.setEval(origin.getEval());
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
		return curr;
	}
	
	
	

	

}
