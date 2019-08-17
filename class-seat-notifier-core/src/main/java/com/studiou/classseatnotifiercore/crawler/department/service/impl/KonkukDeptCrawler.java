package com.studiou.classseatnotifiercore.crawler.department.service.impl;

import com.studiou.classseatnotifiercore.crawler.department.dao.DepartmentCrawlerDao;
import com.studiou.classseatnotifiercore.crawler.department.service.DepartmentCrawler;
import com.studiou.classseatnotifiercore.string.KonkukUri;
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
public class KonkukDeptCrawler implements DepartmentCrawler {
    private final String mainUri = KonkukUri.KONKUK_DEPT_URI;

    @Autowired
    DepartmentCrawlerDao departmentCrawlerDao;

    @Override
    //@Scheduled(fixedDelay = 600000)
    public void departmentSearchScheduler() {
        getDeptInfoFromSource();
    }

    @Override
    public void getDeptInfoFromSource() {
        try{
            Document doc = Jsoup.connect(mainUri).get();
            Elements element = doc.select("SELECT#openSust");
            for (Element el : element.select("OPTION")){
                Map<String, Object> deptInfo = new LinkedHashMap<>();
                deptInfo.put("ID", el.attr("value"));
                deptInfo.put("NAME", el.text());
                this.insertDeptInfo(deptInfo);
                System.out.println(deptInfo.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getDeptCodeList() {
        return departmentCrawlerDao.selectDeptIdList();
    }

    @Override
    public void insertDeptInfo(Map<String, Object> deptInfo) {
        departmentCrawlerDao.insertDepartmentInfo(deptInfo);
    }
}
