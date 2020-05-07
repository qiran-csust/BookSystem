package com.java456.service;

import com.java456.entity.Traffic;

import java.util.List;
import java.util.Map;

public interface TrafficService {
    public void update(Traffic traffic);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Traffic> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
