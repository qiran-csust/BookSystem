package com.java456.service;

import com.java456.entity.Movies;

import java.util.List;
import java.util.Map;

public interface MoviesService {
    public void update(Movies movies);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Movies> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
