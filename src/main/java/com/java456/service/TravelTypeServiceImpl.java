package com.java456.service;

import com.java456.dao.TravelTypeDao;
import com.java456.entity.TravelType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "travelTypeService")
public class TravelTypeServiceImpl implements TravelTypeService {


    @Resource
    private TravelTypeDao travelTypeDao;

    @Override
    public void update(TravelType travelType) {
        TravelType origin = travelTypeDao.findId(travelType.getId());
        travelType = repalce(travelType, origin);
        travelTypeDao.save(travelType);
    }

    /**
     * @param curr   当前更新的数据
     * @param origin 源数据  以前的数据
     * @return curr
     */
    public TravelType repalce(TravelType curr, TravelType origin) {

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
