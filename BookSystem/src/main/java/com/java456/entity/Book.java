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
@Table(name="t_book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length=20)
	@NotEmpty(message="图书名称不能为空！")
	private String name; //图书名称
	@NotNull(message="单价不能为空！")
	@Column(precision = 10, scale = 2)
	private BigDecimal danjia; //单价  fload  double  不建议。
	@NotEmpty(message="作者不能为空！")
	@Column(length=20)
	private String author; //作者
	@NotEmpty(message="出版社不能为空！")
	@Column(length=20)
	private String press; //出版社
	
	
	@NotEmpty(message="图书编号不能为空！")
	@Column(length=20)
	private String bianhao; //图书编号
	@NotNull(message="排序号不能为空！")
	@Column(length=10)
	private Integer orderNo;//排序号
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createDateTime;//创建时间
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updateDateTime;//修改时间
	@Column(length=200)
	private  String imageUrl;//图书封面   
	
	
	@NotNull(message="图书数量不能为空！")
	@Column(length=10)
	private Integer num;//图书数量
	@ManyToOne
	@JoinColumn(name="bookTypeId")
	private BookType bookType; // 图书类型
	
	@ManyToOne
	@JoinColumn(name="bookEvalId")
	private BookEval bookEval; // 图书类型
	
	@Column(length=10)
	private String eval;//
	
	public BookEval getBookEval() {
		return bookEval;
	}
	public void setBookEval(BookEval bookEval) {
		this.bookEval = bookEval;
	}

	public String getEval() {
		return eval;
	}
	public void setEval(String eval) {
		this.eval = eval;
	}
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
	public BigDecimal getDanjia() {
		return danjia;
	}
	public void setDanjia(BigDecimal danjia) {
		this.danjia = danjia;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BookType getBookType() {
		return bookType;
	}
	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}
	
	
	
	
	
	
	
	
}
