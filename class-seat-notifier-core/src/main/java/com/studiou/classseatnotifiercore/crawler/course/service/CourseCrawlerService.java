package com.studiou.classseatnotifiercore.crawler.course.service;

import org.jsoup.select.Elements;

public interface CourseCrawlerService {
    void courseSearchScheduler();
    void getCourseInfoFromSource();
    void insertCourseInfoFromSource(Elements elements, String deptId);
    void updateCourseInfo();
}
