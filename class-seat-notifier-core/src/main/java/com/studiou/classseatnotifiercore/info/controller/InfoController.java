package com.studiou.classseatnotifiercore.info.controller;

import com.studiou.classseatnotifiercore.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/info")
public class InfoController {
    @Autowired
    InfoService infoService;

    @PostMapping(value = "/classwanted")
    public Map<String, Object> wantClassSeat(@RequestParam String classCode){
        Map<String, Object> wantSeatInfo = new LinkedHashMap<>();
        wantSeatInfo.put("CLASS_CODE", classCode);
        wantSeatInfo.put("WANTED_NUM", 1);
        infoService.wantClassSeat(wantSeatInfo);
        return wantSeatInfo;
    }

    @GetMapping(value = "/classlist")
    public List<Map<String, Object>> classList(@RequestParam List<String> keyword){
        return infoService.getClassList(keyword);
    }
    @GetMapping(value = "/remaincourse/normal")
    public List<Map<String, Object>> remainNormalCourseList(){
        return infoService.getNormalRemainCourse();
    }
    @GetMapping(value = "/remaincourse/major")
    public List<Map<String, Object>> remainMajorCourseList(){
        return infoService.getMajorRemainCourse();
    }
}
