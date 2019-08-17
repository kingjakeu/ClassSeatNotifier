package com.studiou.classseatnotifiercore.info.service.impl;

import com.studiou.classseatnotifiercore.info.dao.KonkukInfoDao;
import com.studiou.classseatnotifiercore.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class KonkukInfoService implements InfoService {
    @Autowired
    private KonkukInfoDao konkukInfoDao;

    @Override
    public List<Map<String, Object>> getMyCourseList(Map<String, Object> memberInfo) {
        return konkukInfoDao.selectMyCourseInfoList(memberInfo);
    }

    @Override
    public List<Map<String, Object>> topDeptCourseList(Map<String, Object> memberInfo) {
        return konkukInfoDao.selectTopDeptCourseList(memberInfo);
    }

    @Override
    public List<Map<String, Object>> topNormalCourseList() {
        return konkukInfoDao.selectTopNormalCourseList();
    }

    @Override
    public List<Map<String, Object>> searchCourseList(List<String> keywords) {
        List<Map<String, Object>> courseList = new LinkedList<>();
        for (String keyword : keywords){
            Map<String, Object> searchInfo = new LinkedHashMap<>();
            searchInfo.put("KEYWORD", "%"+keyword+"%");
            List<Map<String, Object>> courseInfos = konkukInfoDao.selectCourseListByKeyword(searchInfo);
            courseList.addAll(courseInfos);
        }
        return courseList;
    }

    @Override
    public void setCourseWantedCap(Map<String, Object> courseInfo) {
        List<String> courseKeyword = new LinkedList<>();
        courseKeyword.add(courseInfo.get("ID").toString());
        Map<String, Object> wantCourseInfo = searchCourseList(courseKeyword).get(0);

        int wantNum = Integer.valueOf(wantCourseInfo.get("WANTED").toString()) + Integer.valueOf(courseInfo.get("WANTED").toString());
        courseInfo.replace("WANTED", String.valueOf(wantNum));
        konkukInfoDao.updateCourseWantedCap(courseInfo);
    }

}
