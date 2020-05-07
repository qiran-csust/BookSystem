package com.java456.dao;

import com.java456.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MoviesDao extends JpaRepository<Movies, Integer>, JpaSpecificationExecutor<Movies> {
    @Query(value = "select * from t_movies where id = ?1", nativeQuery = true)
    public Movies findId(Integer id);

}
