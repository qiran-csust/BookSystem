package com.java456.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java456.entity.UserHistory;;

public interface UserHistoryDao extends JpaRepository<UserHistory,Integer>,JpaSpecificationExecutor<UserHistory> {
	
	
	@Query(value="select * from t_a_user_history where id = ?1",nativeQuery = true)
	public UserHistory findId(Integer id);
	@Query(value="select * from t_a_user_history where user_id = ?1 order by skim_date_time",nativeQuery = true)
	public List<UserHistory> findHistory(@Param("user_id")Integer user_id);
    @Query(value = "SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=1 UNION SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=2 UNION SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=3 UNION SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=4 UNION SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=5 UNION SELECT MAX(order_no) AS LargestOrder FROM t_message WHERE message_type_id=6",nativeQuery=true)
    List<Integer> findUserHistories();
}
