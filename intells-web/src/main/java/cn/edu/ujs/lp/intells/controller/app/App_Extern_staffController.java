package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.search;
import cn.edu.ujs.lp.intells.appservice.entity.Exteam;
import cn.edu.ujs.lp.intells.appservice.entity.Exstaff_role;
import cn.edu.ujs.lp.intells.appservice.entity.Extern_staff;
import cn.edu.ujs.lp.intells.appservice.service.Extern_staffService;
import cn.edu.ujs.lp.intells.appservice.dao.Exstaff_roleRepsitory;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ujs.lp.intells.appservice.dao.Extern_staffRepsitory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 外委职工登录，认证，角色类型Controller
 *
 * @author sher
 * @date 2019-10-13
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/extern_staff")
public class App_Extern_staffController {

    @Autowired
    private Extern_staffService extern_staffService;

    @Autowired
    private Extern_staffRepsitory extern_staffRepsitory;

    @Autowired
    private Exstaff_roleRepsitory exstaff_roleRepsitory;

    //登录
    @PostMapping(value = "/login")
    public JsonResponse find(@RequestBody @Valid Extern_staff request){
        return extern_staffService.find_By_ExstaffLoginName(request.getUser_login_name(),request.getUser_password());
    }

    //冗余 废弃
    @PostMapping(value = "/find_by_id")
    public JsonResponse find_by_id(@RequestBody @Valid Extern_staff request){
        return extern_staffService.find_by_id(request.getId());
    }

    //通过班组id查询班组员工
    @PostMapping(value = "/find_by_exteamId")
    public JsonResponse find_by_exteamId(@RequestBody @Valid Extern_staff request){
        return extern_staffService.find_by_exteamId(request.getExteam_id());
    }

    //主管根据今日排班表查询
    @PostMapping(value = "/find_today_schedule_manager")
    public JsonResponse find_serviceId(@RequestBody @Valid search request){
        return extern_staffService.find_today_schedule_manager(request.getTypeId(),request.getTime() );
    }

    //根据今日排班表查询
    @PostMapping(value = "/find_today_schedule")
    public JsonResponse find_today_schedule(@RequestBody @Valid search request){
        return extern_staffService.find_today_schedule(request.getId(),request.getTime());
    }

    //更新今日排班表员工状态
    @PostMapping(value = "/update_currentstate")
    public JsonResponse update_currentstate(@RequestBody @Valid search request){
        return extern_staffService.update_currentstate(request.getSheetstate(),request.getPerson(),request.getTime());
    }

    //更新今日排班表接单记录
    @PostMapping(value = "/update_orders_received")
    public JsonResponse update_orders_received(@RequestBody @Valid search request){
        return extern_staffService.update_orders_received(request.getPerson(),request.getTime());
    }

    //更新今日排班表完工记录
    @PostMapping(value = "/update_orders_completed")
    public JsonResponse update_orders_completed(@RequestBody @Valid search request){
        return extern_staffService.update_orders_completed(request.getPerson(),request.getTime());
    }


    //获取openid
    @RequestMapping("/getopenid")
    public Object addopenid(HttpServletRequest request) {
        {
            return extern_staffService.getopenid(request);
        }
    }

    //获取微信公众号的token
    @RequestMapping("/getaccess_token")
    public Object getaccess_token(HttpServletRequest request) {
        {
            return extern_staffService.getAccess_Token();
        }
    }

    //添加openid
    @RequestMapping("/addopenid")
    public Object addopenid2(@RequestBody @Valid Extern_staff request) {
        {
            return extern_staffRepsitory.insertByopenid(request.getOpenid(),request.getUser_login_name());
        }
    }

    /*
     * 微信测试账号推送维修工单
     * */
    @RequestMapping("/push")
    public void push(@RequestBody @Valid search request) {
        {
            extern_staffService.push(request.getId(),request.getContent(),request.getGridId());
        }
    }

    /*
     * 微信测试账号推送保洁工单
     * */
    @GetMapping("/cleaningpush")
    public void cleaningpush() {
        {
            extern_staffService.CleaningInspectionpush("2c9a677a6f56abef016f5ee8fd0602c7","111","402880e76f0c9935016f0d06659603cc","2020-01-01 12:00:00","0003");
        }
    }


    //角色类型
    @RequestMapping("/role")
    public Object Exstaff_role(@RequestBody @Valid Exstaff_role request) {
        {
            return exstaff_roleRepsitory.findExstaff_role(request.getUser_id());
        }
    }

   //查询班组组长id
   @RequestMapping("/find_exteamLeader")
   public JsonResponse find_exteamLeader(@RequestBody @Valid search request) {
       {
           return extern_staffService.find_exteamLeader(request.getGridId(),request.getTypeId());
       }
   }




}
