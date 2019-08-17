package com.studiou.classseatnotifiercore.crawler.department.dao;

import com.studiou.classseatnotifiercore.string.NameSpace;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DepartmentCrawlerDao {
    private static final String NAMESPACE = NameSpace.DEPARTMENT_NAMESPACE;

    @Autowired
    private SqlSession sqlSession;

    public List<String> selectDeptIdList(){
        return sqlSession.selectList(NAMESPACE+"selectDeptIdList");
    }
    public void insertDepartmentInfo(Map<String, Object> deptInfo){
        sqlSession.insert(NAMESPACE+"insertDepartmentInfo", deptInfo);
    }
}
