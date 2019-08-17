package com.studiou.classseatnotifiercore.crawler.capacity.dao;

import com.studiou.classseatnotifiercore.string.NameSpace;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Repository
public class KonkukCapCrawlerDao {
    private static final String NAMESPACE = NameSpace.KONKUK_CAPACITY_NAMESPACE;

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, Object>> selectCourseInfoList(){
        return sqlSession.selectList(NAMESPACE+"selectCourseInfoList");
    }
    public void insertCourseCapInfo(Map<String, Object> courseInfo){
        sqlSession.insert(NAMESPACE+"insertCourseCapInfo", courseInfo);
    }
    public void updateCourseCapInfo(Map<String, Object> courseInfo){
        sqlSession.update(NAMESPACE+"updateCourseCapInfo", courseInfo);
    }
}
