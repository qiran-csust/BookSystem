package com.java456.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.java456.entity.Bank;
import com.java456.entity.Message;
import com.java456.entity.MessageType;


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
    public List<Message> selectMessages(String source,Integer message_type_id);
    
    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Message> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
