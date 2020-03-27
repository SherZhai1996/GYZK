//package cn.edu.ujs.lp.intells.controller.app;
//
//
//import cn.edu.ujs.lp.intells.appservice.service.PushService;
//import cn.edu.ujs.lp.intells.appservice.service.InspectionService;
//import cn.edu.ujs.lp.intells.appservice.service.SatisfiedService;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JDDRunner implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        //System.out.println(args);
//        //System.out.println("这个是测试ApplicationRunner接口");
//        SatisfiedService satisfiedService=new SatisfiedService();
//        InspectionService inspectionService=new InspectionService();
//        PushService pushService=new PushService();
//        satisfiedService.UpdateSatisfiedData();
//        inspectionService.findCleaningInspectionResult();
//        pushService.findComprerepairResult();
//        pushService.findTransportAndCleaningResult();
////        String timeStart="2018-20";
////        String timeEnd="2018-20";
////        String content=timeStart+timeEnd+"当前工单已经过期，请及时查看";
////        System.out.print(content);
//    }
//}