package com.java456.service;

import com.java456.dao.EntertainmentTypeDao;
import com.java456.entity.EntertainmentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "entertainmentTypeService")
public class EntertainmentTypeServiceImpl implements EntertainmentTypeService {
    @Resource
    private EntertainmentTypeDao entertainmentTypeDao;

    @Override
    public void update(EntertainmentType entertainmentType) {
        EntertainmentType origin = entertainmentTypeDao.findId(entertainmentType.getId());
        entertainmentType = repalce(entertainmentType, origin);
        entertainmentTypeDao.save(entertainmentType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public EntertainmentType repalce(EntertainmentType curr, EntertainmentType origin) {

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
