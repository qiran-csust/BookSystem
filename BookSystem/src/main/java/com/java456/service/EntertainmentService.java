package com.java456.service;

import com.java456.entity.Entertainment;
import java.util.List;
import java.util.Map;

public interface EntertainmentService {
    public void update(Entertainment entertainment);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Entertainment> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
