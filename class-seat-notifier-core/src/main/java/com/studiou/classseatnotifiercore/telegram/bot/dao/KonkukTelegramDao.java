package com.studiou.classseatnotifiercore.telegram.bot.dao;

import com.studiou.classseatnotifiercore.string.NameSpace;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class KonkukTelegramDao {
    private static final String NAMESPACE = NameSpace.KONKUK_TELEGRAM_NAMESPACE;

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String, Object>> selectTelegramUserList(){
        return sqlSession.selectList(NAMESPACE+"selectTelegramUserList");
    }
    public void insertTelegramUser(Map<String, Object> userInfo){
        sqlSession.insert(NAMESPACE+"insertTelegramUser", userInfo);
    }
}
