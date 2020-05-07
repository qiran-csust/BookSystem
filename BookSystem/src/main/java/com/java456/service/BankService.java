package com.java456.service;

import com.java456.entity.Bank;

import java.util.List;
import java.util.Map;

public interface BankService {
    public void update(Bank bank);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Bank> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}