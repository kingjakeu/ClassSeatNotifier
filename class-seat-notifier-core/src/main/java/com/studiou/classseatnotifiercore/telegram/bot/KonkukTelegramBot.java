package com.studiou.classseatnotifiercore.telegram.bot;

import com.studiou.classseatnotifiercore.info.service.impl.KonkukInfoService;
import com.studiou.classseatnotifiercore.telegram.bot.dao.KonkukTelegramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class KonkukTelegramBot extends TelegramLongPollingBot {

    @Autowired
    KonkukTelegramDao konkukTelegramDao;

    @Autowired
    KonkukInfoService konkukInfoService;

    @Override
    public String getBotUsername() {
        return "AllKill_Bot";
    }

    @Override
    public String getBotToken() {
        return "896546557:AAEEunElp1JzUOslmSrgS7h8iUiJc2yrzx4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.print("FROM TELEGRAM: ");
            System.out.println(update.toString());
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if(messageText.equals("/start")){
                sendMessage(chatId, "올킬 봇 시작!! \n 과목 등록을 원하시면 과목번호 등록(ex. 1234 등록)이라고 메시지를 보내세요.\n 현황을 보내시면 현재 등록한 과목 현황이 뜹니다.");
                this.insertNewTelegramUser(update);
            }else{
                String[] messageArr = messageText.split(" ");
                if(messageArr.length == 2){
                    if(messageArr[1].equals("등록")){
                        Map<String, Object> courseInfo = new LinkedHashMap<>();
                        courseInfo.put("COURSE_ID", messageArr[0]);
                        courseInfo.put("MEMBER_ID", "test");
                        courseInfo.put("WANTED", 1);
                        konkukInfoService.setCourseWantedCap(courseInfo);
                        sendMessage(messageArr[0]+" 등록 됐습니다.");
                    }else{
                        sendMessage("잘못된 입력입니다 :0");
                    }
                }else{
                    if(messageArr[0].equals("현황")){
                        sendRecentCap(chatId);
                    }else{
                        sendMessage("잘못된 입력입니다 :0");
                    }
                }
            }
        }
    }
    public void insertNewTelegramUser(Update update){
        Map<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("CHAT_ID", update.getMessage().getChatId());
        userInfo.put("FIRST_NAME", update.getMessage().getChat().getFirstName());
        userInfo.put("LAST_NAME", update.getMessage().getChat().getLastName());
        konkukTelegramDao.insertTelegramUser(userInfo);
    }
    public void sendRecentCap(long chatId){
        Map<String, Object> memberInfo = new LinkedHashMap<>();
        memberInfo.put("MEMBER_ID", "test");
        List<Map<String, Object>> courseInfo = konkukInfoService.getMyCourseList(memberInfo);
        StringBuilder messageText = new StringBuilder().append("--- 내 수업 현황 ---\n");
        for(Map<String, Object> course :courseInfo){
            messageText.append(course.get("ID").toString()).append("\n");
            messageText.append(course.get("NAME").toString()).append("\n");
            messageText.append(course.get("LOCATION").toString()).append("\n");
            messageText.append("남은 자리 : ");
            messageText.append(course.get("REMAIN").toString());
            messageText.append(" (");
            messageText.append(course.get("ENROLLED").toString()).append("/");
            messageText.append(course.get("TOTAL").toString());
            messageText.append(")").append("\n\n");
        }
        sendMessage(chatId, messageText.toString());
    }
    public void sendMessage(long chatId, String messageText){
        // set the message
        SendMessage message = new SendMessage();
        message.setText(messageText);
        message.setChatId(chatId);
        try{
            execute(message);
        }catch (TelegramApiException te){
            te.printStackTrace();
        }
    }
    public void sendMessage(String messageText){
        // set the message
        SendMessage message = new SendMessage();
        message.setText(messageText);

        // get user list from database
        List<Map<String, Object>> userInfo = konkukTelegramDao.selectTelegramUserList();
        for(Map<String,Object> user : userInfo){
            message.setChatId("");
            try{
                message.setChatId(Long.valueOf(user.get("CHAT_ID").toString()));
                execute(message);
            }catch (TelegramApiException te){
                te.printStackTrace();
            }
        }
    }
}
