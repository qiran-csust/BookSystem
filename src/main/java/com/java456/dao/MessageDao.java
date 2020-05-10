package com.java456.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java456.entity.Bank;
import com.java456.entity.Food;
import com.java456.entity.Message;
import com.java456.entity.MessageType;


public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
    @Query(value = "select * from t_message where id = ?1", nativeQuery = true)
    public Message findId(Integer id);
    @Query(value = "select * from t_message where order_No = ?1", nativeQuery = true)
    public Message findOrderNo(Integer order_No);
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=1 ", nativeQuery = true)
    public  List<Message> findAllMovies();   
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=2 ", nativeQuery = true)
    public  List<Message> findAllTravel();  
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=3 ", nativeQuery = true)
    public  List<Message> findAllFood();   
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=4 ", nativeQuery = true)
    public  List<Message> findAllBank();   
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=5 ", nativeQuery = true)
    public  List<Message> findAllEntertainm();   
    @Query(value = "SELECT * FROM t_message WHERE message_type_id=6 ", nativeQuery = true)
    public  List<Message> findAllTraffic(); 
	@Query(value="select * from t_message where message_type_id=3 order by price ",nativeQuery = true)
	public List<Message> FoodOrderByPrice();

	@Query(value="select * from t_message  where source like CONCAT('%',:source,'%')",nativeQuery=true)
    List<Message> seachMessage(@Param("source") String source);
	@Transactional
	@Modifying
	@Query(value="select * from t_message  where message_type_id=?1 and source like CONCAT('%',?2,'%') order by create_date_time  ",nativeQuery=true)
	List<Message> selectMessages(@Param("message_type_id")Integer message_type_id,@Param("source") String source);
    @Query(value="select * from t_message  order by create_date_time ",nativeQuery=true)
    List<Message> searchNewMessage();

    /**
     * 获取用户收藏的优惠信息
     * @param userId
     * @param startIndex
     * @param pageSize
     */
    @Query(value = "select t.* from t_great_info g, t_message t where g.user_id = ?1 and g.coupons_id = t.id limit ?2, ?3", nativeQuery = true)
    List<Message> selectGreatMessageByPaged(Integer userId, Integer startIndex, Integer pageSize);

    // 加载最近指定类型最近的优惠信息
    @Query(value = "select * from t_message where message_type_id = ?1 and create_date_time between ?2 and ?3", nativeQuery = true)
    List<Message> selectNewMessage(Integer typeId, String startTime, String currTime);

}

