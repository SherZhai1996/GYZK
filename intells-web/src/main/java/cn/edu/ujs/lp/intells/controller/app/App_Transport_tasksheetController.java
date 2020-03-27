package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.dao.Transport_tasksheet_AppsearchRepsitory;
import cn.edu.ujs.lp.intells.appservice.dao.Cleaning_tasksheet_AppsearchRepsitory;
import cn.edu.ujs.lp.intells.appservice.dao.Cleaning_tasksheet_statetrackRepsitory;
import cn.edu.ujs.lp.intells.appservice.dao.Transport_tasksheet_statetrackRepsitory;
import cn.edu.ujs.lp.intells.appservice.dao.ServiceitemcategoryRepsitory;
import cn.edu.ujs.lp.intells.appservice.entity.*;
import cn.edu.ujs.lp.intells.appservice.service.Transport_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运送添加工单，动态查询接口
 *
 * @author sher
 * @date 2019-10-05
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/Transport_tasksheet")
public class App_Transport_tasksheetController {

    @Autowired
    private Transport_tasksheetService transport_tasksheetService;

    @Autowired
    private Transport_tasksheet_statetrackRepsitory transport_tasksheet_statetrackRepsitory;

   //生成工单
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Transport_tasksheet_add transport_tasksheet_add) {

        return transport_tasksheetService.addRecord(transport_tasksheet_add);
    }


    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Transport_tasksheet_undertake transport_tasksheet_undertake) {

        return transport_tasksheetService.undertake(transport_tasksheet_undertake);
    }

    //拒绝派工
    @PostMapping(value = "/delete_undertake")
    public JsonResponse delete_undertake(@RequestBody search search) {

        return transport_tasksheetService.delete_undertake(search.getId(), search.getPicture(), search.getWav());
    }


    //查询主管
    @PostMapping(value = "/find_By_role")
    public Object find_By_role() {

        return  transport_tasksheetService.find_by_role();
    }






    //添加运送服务工单服务事项表
    @PostMapping(value = "/addtransport_tasksheet_serviceitem")
    public JsonResponse addRecord2(@RequestBody Transport_tasksheet_serviceitem transport_tasksheet_serviceitem) {

        return transport_tasksheetService.addRecord2(transport_tasksheet_serviceitem);
    }




    //添加运送服务工单状态表
    @PostMapping(value = "/addtransport_tasksheet_statetrack")
    public JsonResponse addRecord(@RequestBody Transport_tasksheet_statetrack transport_tasksheet_statetrack) {

        return transport_tasksheetService.addRecord3(transport_tasksheet_statetrack);
    }


    //修改运送服务工单服务事项表
    @RequestMapping("/changetransport_tasksheet_statetrack")
    public Object change(@RequestBody @Valid Transport_tasksheet_statetrack request) {
        {
            return transport_tasksheet_statetrackRepsitory.insertByid(request.getTasksheetState(),request.getTasksheetId());
        }
    }



    //添加运送服务工单工具表
    @PostMapping(value = "/addtransport_tasksheet_carryingtools")
    public JsonResponse addRecord(@RequestBody Transport_tasksheet_carryingtools transport_tasksheet_carryingtools) {

        return transport_tasksheetService.addRecord4(transport_tasksheet_carryingtools);
    }

    //添加运送服务工单使用人数
    @PostMapping(value = "/addtransport_tasksheet_additionalinfo")
    public JsonResponse addRecord(@RequestBody Transport_tasksheet_additionalinfo transport_tasksheet_additionalinfo) {

        return transport_tasksheetService.addRecord5(transport_tasksheet_additionalinfo);
    }


    //小组长派工，完工
    @PostMapping(value = "/find_by_state")
    public Object find_by_state(@RequestBody @Valid search request) {


        return  transport_tasksheetService.find_by_state(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }

    //员工完工
    @PostMapping(value = "/find_by_state_exstaff")
    public Object find_by_state_exstaff(@RequestBody @Valid search request) {


        return  transport_tasksheetService.find_by_state_exstaff(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }

    //工单申报人追踪评价（）
    @PostMapping(value = "/assess")
    public Object assess(@RequestBody @Valid search request) {


        return  transport_tasksheetService.assess(request.getPerson(),request.getStartTime(),request.getEndTime());
    }

    //工单申报人追踪评价（）
    @PostMapping(value = "/all_tasksheet")
    public Object all_tasksheet(@RequestBody @Valid search request) {


        return  transport_tasksheetService.all_tasksheet(request.getPerson(),request.getStartTime(),request.getEndTime());
    }


    //项目经理查询
    @PostMapping(value = "/find_by_Department_Manager")
    public Object find_by_Department_Manager(@RequestBody @Valid search request) {


        return  transport_tasksheetService.find_by_Department_Manager(request.getStartTime(),request.getEndTime());
    }

    //提交工单
    @PostMapping(value = "/finish")
    public JsonResponse finish(@RequestBody Transport_tasksheet_finish transport_tasksheet_finish) {

        return transport_tasksheetService.finish(transport_tasksheet_finish);
    }

}
