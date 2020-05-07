package com.java456.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * 存放关注信息的实体
 */

@Entity
@Table(name = "t_focus_info")
public class FocusInfo {
    // id, userId, typeId, createTime, updateTime
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Integer id;
    @Column(name = "user_id", length = 11)
    private Integer userId;         // 用户的id信息
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

    // 添加关联属性
    @ManyToOne(targetEntity = MessageType.class)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private MessageType messageType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
