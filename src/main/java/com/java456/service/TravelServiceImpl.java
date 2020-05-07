package com.java456.service;

import com.java456.dao.TravelDao;
import com.java456.entity.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Service(value = "travelService")
public class TravelServiceImpl implements TravelService {

    @Resource
    private TravelDao travelDao;

    @Override
    public void update(Travel travel) {
        Travel origin = travelDao.findId(travel.getId());
        travel = repalce(travel, origin);
        travelDao.save(travel);
    }

    @Override
    public List<Travel> list(Map<String, Object> map, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Travel> pages = travelDao.findAll(new Specification<Travel>() {
            @Override
            public Predicate toPredicate(Root<Travel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(map.get("travelType") != null){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("travelType"), map.get("travelType")));
                }
                return predicate;
            }
        }, pageable);
        return pages.getContent();
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        Long count = travelDao.count(new Specification<Travel>() {
            @Override
            public Predicate toPredicate(Root<Travel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(map.get("travelType") != null){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("travelType"), map.get("travelType")));
                }
                return predicate;
            }
        });
        return count;
    }

    /**
     * @param curr  当前更新的数据
     * @param origin   源数据  以前的数据
     * @return  curr
     */
    public Travel repalce(Travel curr, Travel origin){

        if(curr.getAddrString()==null){
            curr.setAddrString(origin.getAddrString());
        }
        if(curr.getImageUrl()==null){
            curr.setImageUrl(origin.getImageUrl());
        }
        if(curr.getPrice()==null){
            curr.setPrice(origin.getPrice());
        }
        if(curr.getSource()==null){
            curr.setSource(origin.getSource());
        }


        if(curr.getUrlString()==null){
            curr.setUrlString(origin.getUrlString());
        }
        if(curr.getOrderNo()==null){
            curr.setOrderNo(origin.getOrderNo());
        }
        if(curr.getCreateDateTime()==null){
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if(curr.getUpdateDateTime()==null){
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }
        if(curr.getUrlString() ==null){
            curr.setUrlString(origin.getUrlString());
        }
        if(curr.getTravelType()==null){
            curr.setTravelType(origin.getTravelType());
        }

        return curr;
    }
}
