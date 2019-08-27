package com.studiou.classseatnotifiercore.crawler.auto;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AutoBot {
    private final String sugangUrl = "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp?strSaveCheck=Y&strBrowser=safari&strSbjtId=0034&strKcuCount=0&CuriCdtWarnFg=11.0&MinCuriCnt=1&CuriCnt=4&CuriCdt=18.0&CuriMax=18&CuriAdd=0&PreSngj=3.56&Schdiv=1&strCorsNm=&strDeptCd=&strMultPobtDiv01=";
    private final String loginUrl = "https://kupis.konkuk.ac.kr/sugang/login/loginBtm.jsp?task=f_CourUserLogin&oldStdNo=&ltYy=2019&ltShtm=B01012&campFg=1&stdNo=pado&pwd=dusgml1818%21&idPassGubun=1";

    public void sugangBotExecute(){
        String loginCookie = getLoginCookie();
        sendSugangRequest(sugangUrl, loginCookie);
    }
    private String getLoginCookie(){
        String loginCookie = "";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        try{
            Connection.Response response = Jsoup.connect(loginUrl)
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/login/loginTop.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .method(Connection.Method.POST)
                    .execute();
            Map<String , String> cookies = response.cookies();
            StringBuilder sb = new StringBuilder("WMONID=").append(cookies.get("WMONID")).append("; ")
                                                .append("JSESSIONID=").append(cookies.get("JSESSIONID"));
            loginCookie = sb.toString();
            System.out.println(loginCookie);
        }catch (IOException e){
            e.printStackTrace();
        }
        return loginCookie;
    }
    private void sendSugangRequest(String url, String loginCookie){
        try{
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

            Connection.Response response = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Cookie", loginCookie)
                    .method(Connection.Method.POST)
                    .execute();
            Document doc = response.parse();
            Element inputElement = doc.select("input[NAME=CuriCdt]").first();
            if(inputElement.val().equals("17.0")){
                System.out.println("SUCCESS SUGANG");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
