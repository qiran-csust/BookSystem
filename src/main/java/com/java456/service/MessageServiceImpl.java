package com.java456.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

import com.java456.dao.MessageDao;
import com.java456.entity.Bank;
import com.java456.entity.Message;
import com.java456.entity.MessageType;
import com.java456.entity.User;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService{
	  @Resource
	  private MessageDao messageDao;
	  
	@Override
	public List<Message> seachMessage(String source){
		return  messageDao.seachMessage(source);
	}
	@Override
	public List<Message> searchNewMessages(){
		return	messageDao.searchNewMessage();
				
	}
	@Override
    public  void update(Message message) {
    	Message origin = messageDao.findId(message.getId());
        message = repalce(message, origin);
        messageDao.save(message);

    }
    /**
     * @param curr  当前更新的数据
     * @param origin   源数据  以前的数据
     * @return  curr
     */
    public Message repalce(Message curr, Message origin){

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
        return curr;
    }
	@Override
	public List<Message> findAllFood(){
		return messageDao.findAllFood();
	}
	@Override
	public List<Message> findAllMovies() {
		// TODO Auto-generated method stub
		return messageDao.findAllMovies();
	}
	@Override
	public List<Message> findAllTravel() {
		// TODO Auto-generated method stub
		return messageDao.findAllTravel();
	}
	@Override
	public List<Message> findAllBank() {
		// TODO Auto-generated method stub
		return messageDao.findAllBank();
	}
	@Override
	public List<Message> findAllEntertainm() {
		// TODO Auto-generated method stub
		return messageDao.findAllEntertainm();
	}
	@Override
	public List<Message> findAllTraffic() {
		// TODO Auto-generated method stub
		return messageDao.findAllTraffic();
	}
	@Override
	public List<Message> selectMessages(String source, Integer message_type_id) {
		return messageDao.selectMessages(message_type_id, source);
	}
	
	
	 @Override
	    public List<Message> list(Map<String, Object> map, Integer page, Integer pageSize) {
	        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
	        Page<Message> pages = messageDao.findAll(new Specification<Message>() {
	            @Override
	            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                if(map.get("messageType") != null){
	                    predicate.getExpressions().add(cb.equal(root.get("messageType"), map.get("messageType")));
	                }
	                return predicate;
	            }
	        }, pageable);

	        return pages.getContent();
	    }

	    @Override
	    public Long getTotal(Map<String, Object> map) {
	        Long count = messageDao.count(new Specification<Message>() {
	            @Override
	            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	                // 加入 等于
	                if (map.get("messageType") != null) {
	                    predicate.getExpressions().add(cb.equal(root.get("messageType"), map.get("messageType")));
	                }
	                return predicate;
	            }
	        });
	        return count;
	    }

	/**
	 * 获取用户收藏的优惠券信息
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 */
	@Override
	public List<Message> selectGreatMessageByPaged(Integer userId, Integer pageIndex, Integer pageSize) {
		int startIndex = (pageIndex - 1) * pageSize;
		List<Message> messages = messageDao.selectGreatMessageByPaged(userId, startIndex, pageSize);
		return messages;
	}

	/**
	 * 查询指定类型，最新的优惠信息
	 * @param typeId    类型的id
	 * @param days      时间范围，最近几天的
	 */
	@Override
	public List<Message> selectNewMessage(Integer typeId, Integer days) {
		// 获取当前时间和前days天的日期对象
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currTime = sdf.format(calendar.getTimeInMillis());

		// 设置时间对象为前days天的时间对象
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		String startTime = sdf.format(calendar.getTimeInMillis());

		List<Message> messages = messageDao.selectNewMessage(typeId, startTime, currTime);

		return messages;
	}


}
