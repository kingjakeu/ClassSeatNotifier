package com.studiou.classseatnotifiercore.crawler.department.service;

import java.util.List;
import java.util.Map;

public interface DepartmentCrawler {
    void departmentSearchScheduler();
    void getDeptInfoFromSource();
    List<String> getDeptCodeList();
    void insertDeptInfo(Map<String, Object> deptInfo);
}
