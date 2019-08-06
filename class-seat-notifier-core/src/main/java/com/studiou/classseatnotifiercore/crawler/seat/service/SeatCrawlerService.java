package com.studiou.classseatnotifiercore.crawler.seat.service;


import java.util.List;
import java.util.Map;

public interface SeatCrawlerService {
    void searchSeatScheduler();
    List<Map<String, Object>> getClassCodeList();
    void getSeatDataFromSource(List<Map<String, Object>> classCodeList);
    void updateSeatInfo(Map<String, Object> seatInfo);
}
