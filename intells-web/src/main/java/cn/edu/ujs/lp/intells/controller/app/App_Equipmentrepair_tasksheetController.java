package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Devicebill;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_undertake;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_serviceitem;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_finish;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_statetrack;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_Appsearch;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet;
import cn.edu.ujs.lp.intells.appservice.entity.search;
import cn.edu.ujs.lp.intells.appservice.service.Equipmentrepair_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentrepair_tasksheet_assess;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 设备维修工单添加工单，动态查询接口
 *
 * @author sher
 * @date 2019-10-23
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/equipmentrepair_tasksheet")
public class App_Equipmentrepair_tasksheetController {

    @Autowired
    private Equipmentrepair_tasksheetService equipmentrepair_tasksheetService;

    //设备信息查询
    @PostMapping(value = "/find_by_device_id")
    public JsonResponse find_by_device_id(@RequestBody search request) {

        return equipmentrepair_tasksheetService.find_by_device_id(request.getId());
    }

    //添加新的工单记录
    @PostMapping(value = "/addRecord")
    public JsonResponse addRecord(@RequestBody Equipmentrepair_tasksheet equipmentrepair_tasksheet) {

        return equipmentrepair_tasksheetService.addRecord(equipmentrepair_tasksheet);
    }

    //添加新的工单状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Equipmentrepair_tasksheet_statetrack equipmentrepair_tasksheet_statetrack) {

        return equipmentrepair_tasksheetService.add_statetrack_Record(equipmentrepair_tasksheet_statetrack);
    }

    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Equipmentrepair_tasksheet_undertake equipmentrepair_tasksheet_undertake) {

        return equipmentrepair_tasksheetService.undertake(equipmentrepair_tasksheet_undertake);
    }

    //添加服务事项
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Equipmentrepair_tasksheet_serviceitem equipmentrepair_tasksheet_serviceitem) {

        return equipmentrepair_tasksheetService.add_serviceitem_Record(equipmentrepair_tasksheet_serviceitem);
    }

    //提交工单
    @PostMapping(value = "/add_finish_Record")
    public JsonResponse add_finish_Record(@RequestBody Equipmentrepair_tasksheet_finish equipmentrepair_tasksheet_finish) {

        return equipmentrepair_tasksheetService.add_finish_Record(equipmentrepair_tasksheet_finish);
    }

    //评价工单
    @PostMapping(value = "/add_assess_Record")
    public JsonResponse add_assess_Record(@RequestBody Equipmentrepair_tasksheet_assess equipmentrepair_tasksheet_assess) {

        return equipmentrepair_tasksheetService.add_assess_Record(equipmentrepair_tasksheet_assess);
    }


    //小组长派工，完工
    @PostMapping(value = "/find_by_state")
    public JsonResponse find_by_state(@RequestBody @Valid search request) {


        return  equipmentrepair_tasksheetService.find_by_state(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }

    //员工完工
    @PostMapping(value = "/find_by_state_exstaff")
    public JsonResponse find_by_state_exstaff(@RequestBody @Valid search request) {


        return  equipmentrepair_tasksheetService.find_by_state_exstaff(request.getPerson(),request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }


    //工单申报人追踪评价（）
    @PostMapping(value = "/assess")
    public JsonResponse assess(@RequestBody @Valid search request) {


        return  equipmentrepair_tasksheetService.assess(request.getPerson(),request.getStartTime(),request.getEndTime());
    }



    //项目经理查询
    @PostMapping(value = "/find_by_Department_Manager")
    public JsonResponse find_by_Department_Manager(@RequestBody @Valid search request) {


        return  equipmentrepair_tasksheetService.find_by_Department_Manager(request.getStartTime(),request.getEndTime(),request.getSheetstate());
    }


}
