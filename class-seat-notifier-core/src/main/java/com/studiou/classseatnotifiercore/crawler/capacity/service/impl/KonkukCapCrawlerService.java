package com.studiou.classseatnotifiercore.crawler.capacity.service.impl;

import com.studiou.classseatnotifiercore.crawler.auto.AutoBot;
import com.studiou.classseatnotifiercore.crawler.capacity.dao.KonkukCapCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.capacity.service.CapacityCrawlerService;
import com.studiou.classseatnotifiercore.crawler.course.dao.KonkukCourseCrawlerDao;
import com.studiou.classseatnotifiercore.info.service.impl.KonkukInfoService;
import com.studiou.classseatnotifiercore.string.KonkukUri;
import com.studiou.classseatnotifiercore.telegram.bot.KonkukTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class KonkukCapCrawlerService implements CapacityCrawlerService {
    private final String mainUri = KonkukUri.KONKUK_CAPACITY_MAIN_URI;
    private String optionUri = KonkukUri.KONKUK_CAPACITY_OPT_URI;

    @Autowired
    private KonkukCapCrawlerDao konkukCapCrawlerDao;

    @Autowired
    private KonkukTelegramBot konkukTelegramBot;

    @Autowired
    private KonkukInfoService konkukInfoService;

    @Autowired
    private AutoBot autoBot;

    @Override
    //@Scheduled(fixedRate = 3600000)
    @Scheduled(fixedRate = 1500)
    public void capacitySearchScheduler() {
        System.out.println("------------CAP CRAWL----------");
        this.getCapacityDataFromSource(getCourseInfoList());
    }

    //@Scheduled(fixedRate = 3600000)
    @Scheduled(cron = "0 0 12 * * ?")
    public void allCapacitySearchScheduler() {
        this.getCapacityDataFromSource(getCourseInfoListNoLimit());
    }
    /**
     *
     * */
    @Override
    public void getCapacityDataFromSource(List<Map<String, Object>> courseIdList) {
        StringBuilder capUri = new StringBuilder(mainUri).append(optionUri);
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
    public List<Map<String, Object>> getCourseInfoListNoLimit() {
        List<Map<String, Object>> courseIdList = konkukCapCrawlerDao.selectCourseInfoListNoLimit();
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
            courseInfo.put("REMAIN", remainNum);
            if(remainNum>bfNum){
                //Temp Setting
                System.out.println("hey");
                if(courseInfo.get("COURSE_ID").equals("0733")){
                    System.out.println("no");
                    sendPush(courseInfo);
                }
            }
        }
        courseInfo.put("ENROLLED", elementText.get(0));
        courseInfo.put("TOTAL", elementText.get(1));
        System.out.println(courseInfo.toString());
        updateCapacityInfo(courseInfo);
    }
    @Override
    public void updateCapacityInfo(Map<String, Object> courseInfo) {
        konkukCapCrawlerDao.updateCourseCapInfo(courseInfo);

    }
    private void sendPush(Map<String, Object> courseInfo){
        /// PUSH WHO WANT THIS CLASS
        List<String> keyword = new LinkedList<>();
        keyword.add(courseInfo.get("COURSE_ID").toString());
        List<Map<String, Object>> courseInfoList = konkukInfoService.searchCourseList(keyword);
        StringBuilder messageText = new StringBuilder().append("!! 빈자리 알림 !!").append("\n");
        for (Map<String, Object> notiCourseInfo : courseInfoList){
            messageText.append(notiCourseInfo.get("ID").toString()).append("\n");
            messageText.append(notiCourseInfo.get("NAME").toString()).append("\n");
            messageText.append(courseInfo.get("REMAIN")).append("자리 남았습니다.");
            messageText.append("\n");
        }
        System.out.println("SEND PUSH");
        messageText.append(autoSugang("0733"));
        konkukTelegramBot.sendMessage(messageText.toString());
    }
    private String autoSugang(String courseId){
        String result = courseId+" 신청 시도 ";
        if(courseId.equals("0733")){
             result = result+autoBot.sugangBotExecute();
        }
        return result;
    }
}
