package com.java456.service;

import java.util.List;
import java.util.Map;

import com.java456.entity.UserHistory;

public interface UserHistoryService {
    public List<UserHistory> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
