package com.java456.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.java456.entity.FoodType;
import com.java456.dao.FoodTypeDao;
import com.java456.entity.BookType;

@Service("foodTypeService")
public class FoodTypeServiceImpl implements FoodTypeService {

    @Resource
    private FoodTypeDao foodTypeDao;


    @Override
    public void update(FoodType foodType) {
        FoodType origin = foodTypeDao.findId(foodType.getId());
        //把没有值的数据  换成原数据库的数据。
        foodType = repalce(foodType, origin);
        foodTypeDao.save(foodType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public FoodType repalce(FoodType curr, FoodType origin) {

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
