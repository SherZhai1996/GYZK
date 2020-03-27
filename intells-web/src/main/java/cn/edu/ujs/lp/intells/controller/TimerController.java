package cn.edu.ujs.lp.intells.controller;



import cn.edu.ujs.lp.intells.appservice.service.Extern_staffService;
import cn.edu.ujs.lp.intells.servicecenter.service.WorkSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Configuration          //证明这个类是一个配置文件
@EnableScheduling       //打开quartz定时器总开关
@RestController
public class TimerController {

    @Autowired
    Extern_staffService externStaffService;
    @Autowired
    WorkSchedulingService workSchedulingService;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Scheduled(cron = "0 0 12 * * ?" )//每天中午12点触发
    public void timer(){
        //获取当前时间
       /* LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/
        List<String > result = workSchedulingService.selectUnfinishTasksheet();
        System.out.println("总人数："+result.size());
        for(int i=0;i<result.size();i++){
            System.out.println("aaa");
            System.out.println(result.get(i));
            externStaffService.push(result.get(i),"none","");//测试推送失败
            System.out.println("bbb");
        }
    }
}
