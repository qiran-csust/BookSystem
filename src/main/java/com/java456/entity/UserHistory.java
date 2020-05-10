package com.java456.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name = "t_a_user_history")
public class UserHistory {
	@Id
	private Integer id;
	
	private Integer UserId;
	@Temporal(TemporalType.TIMESTAMP) 
	private Date SkimDateTime;//浏览时间
	@ManyToOne
	@JoinColumn(name="MessageId")
	private Message message; // 图书类型
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getSkimDateTime() {
		return SkimDateTime;
	}
	public void setSkimDateTime(Date skimDateTime) {
		SkimDateTime = skimDateTime;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Integer getUserId() {
		return UserId;
	}
	public void setUserId(Integer userId) {
		UserId = userId;
	}
}
