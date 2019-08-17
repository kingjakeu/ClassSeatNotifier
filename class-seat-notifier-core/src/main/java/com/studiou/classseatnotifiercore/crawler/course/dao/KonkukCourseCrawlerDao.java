package com.studiou.classseatnotifiercore.crawler.course.dao;

import com.studiou.classseatnotifiercore.string.NameSpace;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class KonkukCourseCrawlerDao {
    protected static final String NAMESPACE = NameSpace.KONKUK_COURSE_NAMESPACE;

    @Autowired
    private SqlSession sqlSession;

    public List<String> selectCourseIdList(){
        return sqlSession.selectList(NAMESPACE+"selectCourseIdList");
    }
    public void insertCourseInfo(Map<String, Object> courseInfo){
        sqlSession.insert(NAMESPACE+"insertCourseInfo", courseInfo);
    }
}
