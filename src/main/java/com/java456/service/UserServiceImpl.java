package com.java456.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java456.dao.UserDao;
import com.java456.entity.User;
import com.java456.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao  userDao;
	
	/**
	 * @param curr  当前更新的数据
	 * @param origin   源数据  以前的数据
	 * @return  curr
	 */
	public User repalce(User curr,User origin){
		
		if(curr.getName()==null){
			curr.setName(origin.getName());
		}
		if(curr.getPwd()==null){
			curr.setPwd(origin.getPwd());
		}
		if(curr.getTrueName()==null){
			curr.setTrueName(origin.getTrueName());
		}
		if(curr.getRemark()==null){
			curr.setRemark(origin.getRemark());
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
		if(curr.getRole()==null){
			curr.setRole(origin.getRole());
		}
		
		return curr;
	}
	
	
	@Override
	public Integer update(User user) {
		User origin = userDao.findId(user.getId());
		//把没有值的数据  换成原数据库的数据。
		user = repalce(user, origin);
		
		userDao.save(user);
		return 1;
	}
	
	@Override
	public List<User> list(Map<String, Object> map, Integer page, Integer pageSize) {
		Pageable pageable=new PageRequest(page, pageSize, Sort.Direction.DESC,"orderNo");
		Page<User> list = userDao.findAll(pageable);
		List<User> users = list.getContent();//拿到list集合
		return users;//拿到list集合
	}
	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		Long totaLong = 	userDao.count();
		return totaLong;
	}
	
}
