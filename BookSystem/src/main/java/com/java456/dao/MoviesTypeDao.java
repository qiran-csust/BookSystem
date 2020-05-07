package com.java456.dao;

import com.java456.entity.MoviesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MoviesTypeDao extends JpaRepository<MoviesType, Integer>, JpaSpecificationExecutor<MoviesType> {
    @Query(value = "select * from t_a_movies_type where id = ?1", nativeQuery = true)
    public MoviesType findId(Integer id);
}
