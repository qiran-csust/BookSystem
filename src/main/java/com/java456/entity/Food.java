package com.java456.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="t_food")
public class Food {

	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length=20)
	@NotEmpty(message="！信息来源不能为空")
	private String source; //来源不能为空
	@NotNull(message="价格不能为空！")
	@Column(precision = 10, scale = 2)
	private BigDecimal price; //单价 
	@NotEmpty(message="地址不能为空！")
	@Column(length=20)
	private String addrString; //优惠信息地址
	@NotNull(message="排序号不能为空！")
	@Column(length=10)
	private Integer orderNo;//排序号
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updateDateTime;//修改时间
	@NotNull(message="网址信息不能为空！")
	@Column(length=200)
	private String urlString;//优惠网址
	@Column(length=200)
	private  String imageUrl;//优惠信息封面   
	@ManyToOne
	@JoinColumn(name="FoodTypeId")
	private FoodType foodType; // 图书类型
	
	public FoodType getFoodType() {
		return foodType;
	}
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getAddrString() {
		return addrString;
	}
	public void setAddrString(String addrString) {
		this.addrString = addrString;
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
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	

	
}
