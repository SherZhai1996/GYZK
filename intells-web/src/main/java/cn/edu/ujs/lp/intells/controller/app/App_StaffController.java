package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.dao.StaffRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.Staff;
import cn.edu.ujs.lp.intells.appservice.service.StafffService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 职工登录，认证Controller
 *
 * @author sher
 * @date 2019-09-30
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/staff")
public class App_StaffController {

    @Autowired
    private StafffService staffService;

    //登录
    @PostMapping(value = "/login")
    public JsonResponse find(@RequestBody @Valid Staff request){
        return staffService.login(request.getStaffLoginName(),request.getStaffLoginPassword());
    }

    //查询员工信息
    @PostMapping(value = "/find_by_staffLoginName")
    public JsonResponse find_by_exstaffLoginName(@RequestBody @Valid Staff request){
        return staffService.find_by_staffLoginName(request.getStaffLoginName());
    }

    //冗余  废弃
    @PostMapping(value = "/find_by_id")
    public JsonResponse find_by_id(@RequestBody @Valid Staff request){
        return staffService.find_by_id(request.getId());
    }

//
//    @RequestMapping("/getopenid")
//    public Object addopenid(HttpServletRequest request) {
//        {
//            return extern_staffService.getopenid(request);
//        }
//    }
//
//
//
//    @RequestMapping("/addopenid")
//    public Object addopenid2(@RequestBody @Valid Extern_staff request) {
//        {
//            return extern_staffRepsitory.insertByopenid(request.getOpenid(),request.getExstaffLoginName());
//        }
//    }
//
//    /*
//     * 微信测试账号推送
//     * */
//    @GetMapping("/push")
//    public void push(@RequestBody @Valid Extern_staff request) {
//        {
//            extern_staffService.push(request.getOpenid());
//        }
//    }


}
