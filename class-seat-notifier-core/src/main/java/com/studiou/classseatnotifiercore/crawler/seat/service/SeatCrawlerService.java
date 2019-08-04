package com.studiou.classseatnotifiercore.crawler.seat.service;


import java.util.List;
import java.util.Map;

public interface SeatCrawlerService {
    void searchSeatScheduler();
    List<String> getClassCodeList();
    void getSeatDataFromSource(List<String> classCodeList);
    void updateSeatInfo(Map<String, Object> seatInfo);
}
