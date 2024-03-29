package com.studiou.classseatnotifiercore.info.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InfoDao {
    private static final String TIMETABLE_NAMESPACE = "com.studiou.classseatnotifiercore.timetable.";
    private static final String SEAT_NAMESPACE = "com.studiou.classseatnotifiercore.seat.";

    @Autowired
    private SqlSession sqlSession;

    public void updateWantedSeatNum(Map<String, Object> wantSeatInfo){
        sqlSession.update(SEAT_NAMESPACE+"updateWantedSeatNum", wantSeatInfo);
    }
    public Map<String, Object> selectWantSeatInfo(Map<String, Object> seatInfo) {
        return sqlSession.selectOne(SEAT_NAMESPACE + "selectWantSeatInfo", seatInfo);
    }
    public List<Map<String, Object>> selectClassBasicInfoByKeyword(Map<String, Object> searchInfo){
        return sqlSession.selectList(TIMETABLE_NAMESPACE+"selectClassBasicInfoByKeyword",searchInfo);
    }
}
