package com.java456.service;

import com.java456.dao.MoviesTypeDao;
import com.java456.entity.FoodType;
import com.java456.entity.MoviesType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "moviesTypeService")
public class MoviesTypeServiceImpl implements MoviesTypeService {

    @Resource
    private MoviesTypeDao moviesTypeDao;

    @Override
    public void update(MoviesType moviesType) {
        MoviesType origin = moviesTypeDao.findId(moviesType.getId());
        moviesType = repalce(moviesType, origin);
        moviesTypeDao.save(moviesType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public MoviesType repalce(MoviesType curr, MoviesType origin) {

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
