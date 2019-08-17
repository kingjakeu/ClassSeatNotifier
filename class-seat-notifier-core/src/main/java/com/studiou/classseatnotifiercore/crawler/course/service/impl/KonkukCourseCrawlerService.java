package com.studiou.classseatnotifiercore.crawler.course.service.impl;

import com.studiou.classseatnotifiercore.crawler.course.dao.KonkukCourseCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.course.mapper.KonkukCourseMapper;
import com.studiou.classseatnotifiercore.crawler.course.service.CourseCrawlerService;
import com.studiou.classseatnotifiercore.string.KonkukUri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class KonkukCourseCrawlerService implements CourseCrawlerService {
    private final String mainUri = KonkukUri.KONKUK_COURSE_MAIN_URI;
    private String optionUri =  KonkukUri.KONKUK_COURSE_OPT_URI;

    @Autowired
    private KonkukCourseMapper konkukCourseMapper;

    @Autowired
    private KonkukCourseCrawlerDao konkukCourseCrawlerDao;

    @Override
    //@Scheduled(fixedDelay = 100000)
    @Scheduled(cron = "0 0 12 * * ?")
    public void courseSearchScheduler() {
        this.getCourseInfoFromSource();
    }

    @Override
    public void getCourseInfoFromSource() {
        StringBuilder courseUri = new StringBuilder(mainUri).append(optionUri);
        //TODO : implement get department info
        List<String> deptIdList = new LinkedList<>();
        deptIdList.add("127114");
        try{
            for(String deptId : deptIdList) {
                StringBuilder url = new StringBuilder(courseUri).append(deptId);
                //test
                if (!deptId.equals("127114")) {
                    continue;
                }
                // Get course info From webs
                Document doc = Jsoup.connect(url.toString()).get();
                Elements elements = doc.select("tr.table_bg_white");
                this.insertCourseInfoFromSource(elements ,deptId);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void insertCourseInfoFromSource(Elements elements, String deptId){
        int elementsSize = elements.select("td").size();
        List<String> courseInfo = new ArrayList<>();
        for (int i = 1; i < elementsSize; i++) {
            Element element = elements.select("td").get(i);
            if (i % 18 == 0) {
                courseInfo.add(deptId);
                Map<String, Object> mappedInfo = konkukCourseMapper.courseInfoMapper(courseInfo);
                konkukCourseCrawlerDao.insertCourseInfo(mappedInfo);
                courseInfo = new ArrayList<>();
                System.out.println(mappedInfo.toString());
            } else {
                courseInfo.add(element.text());
            }
        }
    }
    @Override
    public void updateCourseInfo() {
        List<String> courseIdList = konkukCourseCrawlerDao.selectCourseIdList();
        for (String courseId : courseIdList){
            Map<String, Object> courseInfo = new LinkedHashMap<>();
            courseInfo.put("ID", courseId);
            //konkukCourseCrawlerDao.insertCourseInfo(courseInfo);
        }
    }
}
