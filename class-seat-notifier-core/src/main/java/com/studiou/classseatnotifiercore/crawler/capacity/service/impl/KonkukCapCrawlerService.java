package com.studiou.classseatnotifiercore.crawler.capacity.service.impl;

import com.studiou.classseatnotifiercore.crawler.capacity.dao.KonkukCapCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.capacity.service.CapacityCrawlerService;
import com.studiou.classseatnotifiercore.crawler.course.dao.KonkukCourseCrawlerDao;
import com.studiou.classseatnotifiercore.string.KonkukUri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KonkukCapCrawlerService implements CapacityCrawlerService {
    private final String mainUri = KonkukUri.KONKUK_CAPACITY_MAIN_URI;
    private String optionUri = KonkukUri.KONKUK_CAPACITY_OPT_URI;

    @Autowired
    private KonkukCapCrawlerDao konkukCapCrawlerDao;

    @Override
    @Scheduled(fixedRate = 50000000)
    public void capacitySearchScheduler() {
        this.getCapacityDataFromSource();
    }

    @Override
    public void getCapacityDataFromSource() {
        StringBuilder capUri = new StringBuilder(mainUri).append(optionUri);
        List<Map<String, Object>> courseIdList = getCourseInfoList();
        try{
            for(Map<String, Object> courseInfo : courseIdList){
                String courseId = courseInfo.get("COURSE_ID").toString();
                Document doc = Jsoup.connect(new StringBuilder(capUri).append(courseId).toString()).post();
                Elements elements = doc.select("td.table_bg_white");
                this.insertCapInfoFromSource(elements, courseInfo);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getCourseInfoList() {
        List<Map<String, Object>> courseIdList = konkukCapCrawlerDao.selectCourseInfoList();
        return courseIdList;
    }
    public void insertCapInfoFromSource(Elements elements, Map<String, Object> courseInfo){
        ArrayList<String> elementText = new ArrayList<>();
        int elementSize = elements.size();
        for (int i=1; i<elementSize; i++){
            elementText.add(elements.get(i).text());
        }
        // get remain caps
        int remainNum = Integer.parseInt(elementText.get(1)) - Integer.parseInt(elementText.get(0));

        if(courseInfo.get("REMAIN") != null){
            int bfNum = Integer.parseInt(courseInfo.get("REMAIN").toString());
            if(remainNum>bfNum){
                /// PUSH WHO WANT THIS CLASS
                System.out.println("SEND PUSH");
            }
        }
        courseInfo.put("REMAIN", remainNum);
        courseInfo.put("ENROLLED", elementText.get(0));
        courseInfo.put("TOTAL", elementText.get(1));
        System.out.println(courseInfo.toString());
        updateCapacityInfo(courseInfo);
    }
    @Override
    public void updateCapacityInfo(Map<String, Object> courseInfo) {
        konkukCapCrawlerDao.updateCourseCapInfo(courseInfo);

    }
}
