package com.java456.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="t_a_Message_type")
public class MessageType {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length=10)
	private String name; //类型名称
	@Column(length=10)
	private Integer orderNo;
	//创建时间
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;
	//修改时间
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updateDateTime;

	// 添加关联属性
	@OneToMany(mappedBy = "messageType")
	private Set<FocusInfo> focusInfos = new HashSet<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}
