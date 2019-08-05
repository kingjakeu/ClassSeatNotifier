package com.studiou.classseatnotifiercore.crawler.major.service;

import java.util.List;
import java.util.Map;

public interface MajorInfoCrawler {
    void searchMajorInfoScheduler();
    void getMajorInfoFromSource();
    List<String> getMajorCodeList();
    void insertMajorInfo(Map<String, Object> majorInfo);
}
