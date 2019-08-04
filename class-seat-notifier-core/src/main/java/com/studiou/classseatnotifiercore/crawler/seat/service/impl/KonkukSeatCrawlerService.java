package com.studiou.classseatnotifiercore.crawler.seat.service.impl;

import com.studiou.classseatnotifiercore.crawler.seat.dao.SeatCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.seat.service.SeatCrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KonkukSeatCrawlerService implements SeatCrawlerService {
    private final String mainUri = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?";
    private String optionUri = "ltYy=2019&ltShtm=B01012&sbjtId=";
    @Autowired
    private SeatCrawlerDao seatCrawlerDao;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void searchSeatScheduler() {
        getSeatDataFromSource(getClassCodeList());
    }

    @Override
    public List<String> getClassCodeList() {
        List<String> classCodeList = seatCrawlerDao.selectClassCodeList();
        return classCodeList;
    }

    @Override
    public void getSeatDataFromSource(List<String> classCodeList) {
        String url = mainUri+optionUri;

        for(String classCode : classCodeList) {

            Map<String, Object> seatInfo = new HashMap<>();
            seatInfo.put("CLASS_CODE", classCode);
            url = url + "2038";

            try {
                Document doc = Jsoup.connect(url).post();
                Elements element = doc.select("td.table_bg_white");

                System.out.println(element.toString());
                int count = 0;
//                for (Element el : element.select("td.table_bg_white")) {
//                    System.out.println(el.text());
////                if(count == 1){
////                    seatInfo.put("REMAIN_NUM", Integer.parseInt(el.text()));
////                }else if(count == 2){
////                    seatInfo.put("TOTAL_NUM", Integer.parseInt(el.text()));
////                }
//                    count++;
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(seatInfo.toString());
            //updateSeatInfo(seatInfo);
        }
    }

    @Override
    public void updateSeatInfo(Map<String, Object> seatInfo) {
        seatCrawlerDao.updateClassSeatInfo(seatInfo);
    }
}
