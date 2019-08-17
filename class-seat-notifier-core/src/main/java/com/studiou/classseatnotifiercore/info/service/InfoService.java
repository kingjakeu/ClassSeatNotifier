package com.studiou.classseatnotifiercore.info.service;

import java.util.List;
import java.util.Map;
public interface InfoService {
    List<Map<String, Object>> getMyCourseList(Map<String, Object> memberInfo);
    List<Map<String, Object>> topDeptCourseList(Map<String, Object> memberInfo);
    List<Map<String, Object>> topNormalCourseList();
    List<Map<String, Object>> searchCourseList(List<String> keywords);
    void setCourseWantedCap(Map<String, Object> courseInfo);
}
