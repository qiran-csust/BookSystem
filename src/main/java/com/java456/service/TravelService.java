package com.java456.service;

import com.java456.entity.Travel;

import java.util.List;
import java.util.Map;

public interface TravelService {
    public void update(Travel travel);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Travel> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
