package com.java456.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.java456.entity.Bank;
import com.java456.entity.Message;


public interface MessageService {
	 public void update(Message message);

	public List<Message> findAllMovies();   
	public List<Message> findAllTravel();  
	public List<Message> findAllBank();    
	public List<Message> findAllEntertainm();   
	public List<Message> findAllTraffic();  
	public List<Message> findAllFood();
    public List<Message> seachMessage(String source);
    public List<Message> searchNewMessages();
    
    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    List<Message> list(Map<String,Object> map, Integer page, Integer pageSize);

    Long getTotal(Map<String,Object> map);

    List<Message> selectGreatMessageByPaged(Integer userId, Integer pageIndex, Integer pageSize);

	/**
	 *	查询指定类型最新的优惠信息
	 * @param typeId	类型的id
	 * @param days		表示是最近多少天的优惠信息。比如：days=2,这表示最近两天的优惠信息
	 */
	List<Message> selectNewMessage(Integer typeId, Integer days);
}
