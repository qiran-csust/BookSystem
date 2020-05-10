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

import com.java456.dao.UserHistoryDao;
import com.java456.entity.Message;
import com.java456.entity.UserHistory;
@Service("userHistorySerivce")
public class UserHistoryServiceImpl implements UserHistoryService{
	@Resource
	private UserHistoryDao userHistoryDao;
	@Override
	public List<UserHistory> list(Map<String, Object> map, Integer page, Integer pageSize) {
		  Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
	        Page<UserHistory> pages = userHistoryDao.findAll(new Specification<UserHistory>() {
	            @Override
	            public Predicate toPredicate(Root<UserHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                if(map.get("messageId") != null){
	                    predicate.getExpressions().add(cb.equal(root.get("messageId"), map.get("messageId")));
	                }
	                return predicate;
	            }
	        }, pageable);

	        return pages.getContent();
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		  Long count = userHistoryDao.count(new Specification<UserHistory>() {
	            @Override
	            public Predicate toPredicate(Root<UserHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                // 加入 等于
	                if (map.get("messageId") != null) {
	                    predicate.getExpressions().add(cb.equal(root.get("messageId"), map.get("messageId")));
	                }
	                return predicate;
	            }
	        });
	        return count;
	    }
	
}
