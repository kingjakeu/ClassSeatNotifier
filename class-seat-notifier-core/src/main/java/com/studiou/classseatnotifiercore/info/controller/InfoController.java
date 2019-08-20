package com.studiou.classseatnotifiercore.info.controller;

import com.studiou.classseatnotifiercore.info.service.InfoService;
import com.studiou.classseatnotifiercore.info.service.impl.KonkukInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/info")
public class InfoController {
    @Autowired
    InfoService infoService;

    @Autowired
    KonkukInfoService konkukInfoService;

    @GetMapping(value = "/mycourselist")
    public List<Map<String, Object>> myClassList(@RequestParam Map<String, Object> memberInfo){
        return konkukInfoService.getMyCourseList(memberInfo);
    }
    @GetMapping(value = "/toplist/department")
    public List<Map<String, Object>> topDeptCourseList(@RequestParam Map<String, Object> memberInfo){
        return konkukInfoService.topDeptCourseList(memberInfo);
    }
    @GetMapping(value = "/toplist/normal")
    public List<Map<String, Object>> topNormalCourseList(){
        return konkukInfoService.topNormalCourseList();
    }

    @GetMapping(value = "/searchcourse")
    public List<Map<String, Object>> searchCourse(@RequestParam List<String> keyword){
        return konkukInfoService.searchCourseList(keyword);
    }

    @PostMapping(value = "/wantcoursecap")
    public Map<String, Object> courseWantedCap(@RequestParam Map<String, Object> wantCourseInfo){
        wantCourseInfo.put("WANTED", 1);
        konkukInfoService.setCourseWantedCap(wantCourseInfo);
        return wantCourseInfo;
    }
}
