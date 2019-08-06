package com.studiou.classseatnotifiercore.crawler.classinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class  ClassInfoCrawlerDao {

    protected static final String NAMESPACE = "com.studiou.classseatnotifiercore.timetable.";

    @Autowired
    private SqlSession sqlSession;

    public List<String> selectClassCodeList(){
        return sqlSession.selectList(NAMESPACE+"selectClassCodeList");
    }
    public void insertClassInfo(Map<String, Object> seatInfo){
        sqlSession.insert(NAMESPACE+"insertClassInfo", seatInfo);
    }
}

