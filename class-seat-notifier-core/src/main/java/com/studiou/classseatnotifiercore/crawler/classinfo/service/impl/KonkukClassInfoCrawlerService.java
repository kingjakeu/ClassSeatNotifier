package com.studiou.classseatnotifiercore.crawler.classinfo.service.impl;

import com.studiou.classseatnotifiercore.crawler.classinfo.dao.ClassInfoCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.classinfo.mapper.ClassInfoMapper;
import com.studiou.classseatnotifiercore.crawler.classinfo.service.ClassInfoCrawlerService;
import com.studiou.classseatnotifiercore.crawler.major.dao.MajorInfoCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.major.service.MajorInfoCrawler;
import com.studiou.classseatnotifiercore.crawler.seat.dao.SeatCrawlerDao;
import com.studiou.classseatnotifiercore.util.ConstStrings;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
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
public class KonkukClassInfoCrawlerService implements ClassInfoCrawlerService {
    private final String mainUri = "https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?";
    private String optionUri = "ltYy="+ ConstStrings.YEAR+"&ltShtm="+ConstStrings.SEMESTER+"&openSust=";

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Autowired
    private ClassInfoCrawlerDao classInfoCrawlerDao;

    @Autowired
    private SeatCrawlerDao seatCrawlerDao;

    @Autowired
    private MajorInfoCrawler majorInfoCrawler;

    @Override
    @Scheduled(fixedDelay = 86400000)
    public void searchClassInfoScheduler() {
        getClassInfoFromSource();
        updateClassInfo();
    }

    @Override
    public void getClassInfoFromSource() {
        List<String> majorCodeList = getMajorCodeList();

        for(String majorCode : majorCodeList){
            Document doc = null;
            String url = mainUri + optionUri+ majorCode;
            //test
            if(!majorCode.equals("127114")){
                continue;
            }
            try{
                doc = Jsoup.connect(url).get();
                Elements element = doc.select("tr.table_bg_white");
                int leng = element.select("td").size();
                List<String> info = new ArrayList<>();
                for (int i=1; i<leng; i++){
                    Element el = element.select("td").get(i);
                    if(i%18 == 0){
                        Map<String, Object> mappedInfo = classInfoMapper.classInfoMapper(info);
                        classInfoCrawlerDao.insertClassInfo(mappedInfo);
                        info = new ArrayList<>();
                    }else {
                        info.add(el.text());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateClassInfo() {
        List<String> classCodeList = classInfoCrawlerDao.selectClassCodeList();
        for (String classCode : classCodeList){
            Map<String, Object> classInfo = new LinkedHashMap<>();
            classInfo.put("CLASS_CODE", classCode);
            seatCrawlerDao.insertClassSeatInfo(classInfo);
        }
    }

    public List<String> getMajorCodeList(){
        return majorInfoCrawler.getMajorCodeList();
    }
}