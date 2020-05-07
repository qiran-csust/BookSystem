package com.java456.service;

import com.java456.dao.BankTypeDao;
import com.java456.entity.BankType;
import com.java456.entity.FoodType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "bankTypeService")
public class BankTypeServiceImpl implements BankTypeService {

    @Resource
    private BankTypeDao bankTypeDao;

    @Override
    public void update(BankType bankType) {
        BankType origin = bankTypeDao.findId(bankType.getId());
        // 把没有值的数据替换成数据库的数据
        bankType = repalce(bankType, origin);
        bankTypeDao.save(bankType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public BankType repalce(BankType curr, BankType origin) {

        if (curr.getName() == null) {
            curr.setName(origin.getName());
        }
        if (curr.getOrderNo() == null) {
            curr.setOrderNo(origin.getOrderNo());
        }
        if (curr.getCreateDateTime() == null) {
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if (curr.getUpdateDateTime() == null) {
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }
        return curr;
    }
}
