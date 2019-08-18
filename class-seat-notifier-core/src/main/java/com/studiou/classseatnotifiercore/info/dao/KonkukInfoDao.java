package com.studiou.classseatnotifiercore.info.dao;

import com.studiou.classseatnotifiercore.string.NameSpace;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class KonkukInfoDao {
    private static final String INFO_NAMESPACE = NameSpace.KONKUK_INFO_NAMESPACE;

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, Object>> selectMyCourseInfoList(Map<String, Object> memberInfo){
        return sqlSession.selectList(INFO_NAMESPACE+"selectMyCourseInfoList",memberInfo);
    }
    public List<Map<String, Object>> selectTopDeptCourseList(Map<String, Object> memberInfo){
        return sqlSession.selectList(INFO_NAMESPACE+"selectTopDeptCourseList", memberInfo);
    }
    public List<Map<String, Object>> selectTopNormalCourseList(){
        return sqlSession.selectList(INFO_NAMESPACE+"selectTopNormalCourseList");
    }
    public List<Map<String, Object>> selectCourseListByKeyword(Map<String, Object> searchInfo){
        return sqlSession.selectList(INFO_NAMESPACE+"selectCourseListByKeyword", searchInfo);
    }
    public Map<String, Object> selectCourseInfoByCourseId(Map<String, Object> searchInfo){
        return sqlSession.selectOne(INFO_NAMESPACE+"selectCourseInfoByCourseId", searchInfo);
    }
    public void updateCourseWantedCap(Map<String, Object> courseInfo){
        sqlSession.update(INFO_NAMESPACE+"updateCourseWantedCap",courseInfo);
    }
    public void insertCourseWanted(Map<String, Object> courseInfo){
        sqlSession.insert(INFO_NAMESPACE+"insertCourseWanted", courseInfo);
    }
}
