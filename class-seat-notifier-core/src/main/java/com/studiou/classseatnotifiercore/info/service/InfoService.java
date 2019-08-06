package com.studiou.classseatnotifiercore.info.service;

import com.studiou.classseatnotifiercore.info.dao.InfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoService {

    @Autowired
    InfoDao infoDao;

    public void wantClassSeat(Map<String, Object> reqSeatInfo){
        Map<String, Object> wantSeatInfo = infoDao.selectWantSeatInfo(reqSeatInfo);
        int wantNum = Integer.valueOf(wantSeatInfo.get("WANTED_NUM").toString()) + Integer.valueOf(reqSeatInfo.get("WANTED_NUM").toString());
        reqSeatInfo.replace("WANTED_NUM", String.valueOf(wantNum));
        infoDao.updateWantedSeatNum(reqSeatInfo);
    }
}
