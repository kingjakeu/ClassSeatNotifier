package com.studiou.classseatnotifiercore.crawler.seat.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SeatCrawlerDao {
    protected static final String NAMESPACE = "com.studiou.classseatnotifiercore.seat.";

    @Autowired
    private SqlSession sqlSession;

    public List<String> selectClassCodeList(){
        return sqlSession.selectList(NAMESPACE+"selectClassCodeList");
    }
    public void insertClassSeatInfo(Map<String, Object> seatInfo){
        sqlSession.insert(NAMESPACE+"insertClassSeatInfo", seatInfo);
    }
    public void updateClassSeatInfo(Map<String, Object> seatInfo){
        sqlSession.update(NAMESPACE+"updateClassSeatInfo", seatInfo);
    }
}
