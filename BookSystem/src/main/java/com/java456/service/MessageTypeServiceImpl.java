package com.java456.service;

import com.java456.dao.MessageTypeDao;
import com.java456.entity.MessageType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageTypeServiceImpl implements MessageTypeService {

    @Resource
    private MessageTypeDao messageTypeDao;


    /**
     * 根据类型的名称获取类型对象
     * @param typeName
     * @return
     */
    @Override
    public MessageType getMessageTypeByName(String typeName) {
        MessageType messageType = messageTypeDao.getMessageTypeByName(typeName);
        return messageType;
    }
}
