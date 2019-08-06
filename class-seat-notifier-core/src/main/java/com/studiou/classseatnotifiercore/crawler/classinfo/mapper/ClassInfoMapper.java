package com.studiou.classseatnotifiercore.crawler.classinfo.mapper;

import com.studiou.classseatnotifiercore.util.ConstStrings;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassInfoMapper {
    public Map<String, Object> classInfoMapper(List<String> info){
        Map<String, Object> classInfo = new LinkedHashMap<>();
        classInfo.put("CLASS_CODE", info.get(3));
        classInfo.put("NAME", info.get(4));
        classInfo.put("GRADE", info.get(0));
        classInfo.put("TYPE", info.get(2));
        classInfo.put("CREDIT", Integer.parseInt(info.get(5)));
        classInfo.put("INSTRUCTOR", info.get(9));
        classInfo.put("LOCATION", info.get(8));
        classInfo.put("MAJOR", info.get(17));
        classInfo.put("YEAR", ConstStrings.YEAR);
        classInfo.put("SEMESTER", ConstStrings.SEMESTER);
        System.out.println(classInfo.toString());
        return classInfo;
    }
}
