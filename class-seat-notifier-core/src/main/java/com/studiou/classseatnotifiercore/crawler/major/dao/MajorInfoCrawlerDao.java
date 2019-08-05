package com.studiou.classseatnotifiercore.crawler.major.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MajorInfoCrawlerDao {
    protected static final String NAMESPACE = "com.studiou.classseatnotifiercore.major.";

    @Autowired
    private SqlSession sqlSession;

    public List<String> selectMajorCodeList(){
        return sqlSession.selectList(NAMESPACE+"selectMajorCodeList");
    }
    public void insertMajorInfo(Map<String, Object> majorInfo){
        sqlSession.insert(NAMESPACE+"insertMajorInfo", majorInfo);
    }
}
