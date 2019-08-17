package com.studiou.classseatnotifiercore.crawler.seat.service.impl;

import com.studiou.classseatnotifiercore.crawler.seat.dao.SeatCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.seat.service.SeatCrawlerService;
import com.studiou.classseatnotifiercore.util.ConstStrings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KonkukSeatCrawlerService implements SeatCrawlerService {
    private final String mainUri = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?";
    private String optionUri = "ltYy="+ ConstStrings.YEAR+"&ltShtm="+ConstStrings.SEMESTER+"&sbjtId=";
    @Autowired
    private SeatCrawlerDao seatCrawlerDao;

    @Override
    @Scheduled(fixedRate = 50000000)
    public void searchSeatScheduler() {
        getSeatDataFromSource(getClassCodeList());
    }

    @Override
    public List<Map<String, Object>> getClassCodeList() {
        List<Map<String, Object>> classCodeList = seatCrawlerDao.selectClassCodeList();
        return classCodeList;
    }

    @Override
    public void getSeatDataFromSource(List<Map<String, Object>> classCodeList) {
        String url = mainUri+optionUri;

        for(Map<String, Object> seatInfo : classCodeList) {
            String classCode = seatInfo.get("CLASS_CODE").toString();
            try {
                Document doc = Jsoup.connect(url+classCode).post();
                Elements element = doc.select("td.table_bg_white");
                ArrayList<String> elementText = new ArrayList<>();
                int elementSize = element.size();
                for (int i=1; i<elementSize; i++){
                    elementText.add(element.get(i).text());
                }
                int bfNum = Integer.parseInt(seatInfo.get("REMAIN_NUM").toString());
                int remainNum = Integer.parseInt(elementText.get(1)) - Integer.parseInt(elementText.get(0));

                if(remainNum>bfNum){
                    /// PUSH WHO WANT THIS CLASS
                    System.out.println("SEND PUSH");
                }
                seatInfo.put("REMAIN_NUM", remainNum);
                seatInfo.put("TOTAL_NUM", elementText.get(1));

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(seatInfo.toString());
            updateSeatInfo(seatInfo);
        }
    }

    @Override
    public void updateSeatInfo(Map<String, Object> seatInfo) {
        seatCrawlerDao.updateClassSeatInfo(seatInfo);
    }
}
