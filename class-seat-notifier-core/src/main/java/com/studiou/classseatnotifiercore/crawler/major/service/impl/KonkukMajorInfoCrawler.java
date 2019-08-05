package com.studiou.classseatnotifiercore.crawler.major.service.impl;

import com.studiou.classseatnotifiercore.crawler.major.dao.MajorInfoCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.major.service.MajorInfoCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class KonkukMajorInfoCrawler implements MajorInfoCrawler {
    private final String mainUri = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp";

    @Autowired
    MajorInfoCrawlerDao majorInfoCrawlerDao;

    @Override
    //@Scheduled(fixedDelay = 600000)
    public void searchMajorInfoScheduler() {
        getMajorInfoFromSource();
    }

    @Override
    public void getMajorInfoFromSource() {
        Document doc = null;
        try{
            doc = Jsoup.connect(mainUri).get();
        }catch (IOException e){
            e.printStackTrace();
        }
        Elements element = doc.select("SELECT#openSust");
        for (Element el : element.select("OPTION")){
            Map<String, Object> majorInfo = new LinkedHashMap<>();
            majorInfo.put("MAJOR_CODE", el.attr("value"));
            majorInfo.put("MAJOR_NAME", el.text());
            System.out.println(majorInfo.toString());
            majorInfoCrawlerDao.insertMajorInfo(majorInfo);
        }

    }

    @Override
    public List<String> getMajorCodeList() {
        return majorInfoCrawlerDao.selectMajorCodeList();
    }

    @Override
    public void insertMajorInfo(Map<String, Object> majorInfo) {
        majorInfoCrawlerDao.insertMajorInfo(majorInfo);
    }
}
