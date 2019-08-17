package com.studiou.classseatnotifiercore.crawler.course.mapper;

import com.studiou.classseatnotifiercore.util.ConstStrings;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class KonkukCourseMapper {
    public Map<String, Object> courseInfoMapper(List<String> info){
        Map<String, Object> courseInfo = new LinkedHashMap<>();
        courseInfo.put("ID", info.get(3));
        courseInfo.put("NAME", info.get(4));
        courseInfo.put("INSTRUCTOR", info.get(9));
        courseInfo.put("TYPE", info.get(2));
        courseInfo.put("CREDIT", Integer.parseInt(info.get(5)));
        courseInfo.put("GRADE", info.get(0));
        courseInfo.put("LOCATION", info.get(8));
        courseInfo.put("DEPT_ID", info.get(17));
        courseInfo.put("YEAR", ConstStrings.YEAR);
        courseInfo.put("SEMESTER", ConstStrings.SEMESTER);
        return courseInfo;
    }
}
