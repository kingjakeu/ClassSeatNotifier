package com.studiou.classseatnotifiercore.crawler.capacity.service;

import java.util.List;
import java.util.Map;

public interface CapacityCrawlerService {
    void capacitySearchScheduler();
    List<Map<String, Object>> getCourseInfoList();
    void getCapacityDataFromSource(List<Map<String, Object>> courseIdList);
    void updateCapacityInfo(Map<String, Object> capacityInfo);
}
