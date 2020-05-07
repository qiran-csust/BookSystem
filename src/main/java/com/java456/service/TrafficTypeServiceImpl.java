package com.java456.service;

import com.java456.dao.TrafficTypeDao;
import com.java456.entity.FoodType;
import com.java456.entity.TrafficType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "trafficTypeService")
public class TrafficTypeServiceImpl implements TrafficTypeService {

    @Resource
    private TrafficTypeDao trafficTypeDao;

    @Override
    public void update(TrafficType trafficType) {
        TrafficType origin = trafficTypeDao.findId(trafficType.getId());
        trafficType = repalce(trafficType, origin);
        trafficTypeDao.save(trafficType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public TrafficType repalce(TrafficType curr, TrafficType origin) {

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
