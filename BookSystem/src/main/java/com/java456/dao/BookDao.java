package com.java456.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.java456.entity.Book;
import com.java456.entity.BookType;

public interface BookDao extends JpaRepository<Book,Integer>,JpaSpecificationExecutor< Book> {
	
	
	@Query(value="select * from t_book where id = ?1",nativeQuery = true)
	public Book  findId(Integer id);
	
	
}
