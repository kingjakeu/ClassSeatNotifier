package com.studiou.classseatnotifiercore.crawler.capacity.service;

import java.util.List;
import java.util.Map;

public interface CapacityCrawlerService {
    void capacitySearchScheduler();
    List<Map<String, Object>> getCourseInfoList();
    void getCapacityDataFromSource();
    void updateCapacityInfo(Map<String, Object> capacityInfo);
}
