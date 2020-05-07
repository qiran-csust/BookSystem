package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.MessageType;

public interface MessageTypeDao extends JpaRepository<MessageType, Integer>, JpaSpecificationExecutor<MessageType> {
    
	@Query(value = "select * from t_a_Message_type where id = ?1", nativeQuery = true)
    MessageType findId(Integer id);

	@Query(value = "select * from t_a_message_type where name = ?1", nativeQuery = true)
	MessageType getMessageTypeByName(String typeName);
}