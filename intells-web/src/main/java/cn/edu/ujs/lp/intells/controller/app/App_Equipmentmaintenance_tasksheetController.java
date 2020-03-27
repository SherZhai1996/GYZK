package cn.edu.ujs.lp.intells.controller.app;


import cn.edu.ujs.lp.intells.appservice.entity.*;
import cn.edu.ujs.lp.intells.appservice.dao.Comprerepair_tasksheet_AppsearchRepsitory;
import cn.edu.ujs.lp.intells.appservice.service.Equipmentmaintenance_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 添加工单，动态查询接口
 *
 * @author sher
 * @date 2019-10-22
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/equipmentmaintenance_tasksheet")
public class App_Equipmentmaintenance_tasksheetController {

    @Autowired
    private Equipmentmaintenance_tasksheetService equipmentmaintenance_tasksheetService;

    @PostMapping(value = "/find_by_planId")
    public JsonResponse find_by_planId(@RequestBody search request) {

        return equipmentmaintenance_tasksheetService.find_by_planId(request.getId());
    }

    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Equipmentmaintenance_tasksheet_undertake equipmentmaintenance_tasksheet_undertake) {

        return equipmentmaintenance_tasksheetService.undertake(equipmentmaintenance_tasksheet_undertake);
    }

    //员工 设备保养查询
    @PostMapping(value = "/find_by_exstaff")
    public JsonResponse find_by_exstaff(@RequestBody @Valid search request) {


        return  equipmentmaintenance_tasksheetService.find_by_exstaff(request.getPerson(),request.getSheetstate());
    }

    //员工 工单项目查询
    @PostMapping(value = "/find_by_device_id")
    public JsonResponse find_by_device_id(@RequestBody @Valid search request) {


        return  equipmentmaintenance_tasksheetService.find_by_device_id(request.getId(),request.getPerson(),request.getTime());
    }

    //班组长 工单项目查询
    @PostMapping(value = "/find_by_GM")
    public JsonResponse find_by_GM(@RequestBody @Valid search request) {


        return  equipmentmaintenance_tasksheetService.find_by_GM(request.getPerson(),request.getSheetstate());
    }

    //主管 工单项目查询
    @PostMapping(value = "/find_by_manager")
    public JsonResponse find_by_manager(@RequestBody @Valid search request) {


        return  equipmentmaintenance_tasksheetService.find_by_manager(request.getSheetstate());
    }

    //员工 工单项目内容查询
    @PostMapping(value = "/findByTemplateId")
    public JsonResponse findByTemplateId(@RequestBody @Valid Equipmentmaintenance_item request) {


        return  equipmentmaintenance_tasksheetService.findByTemplateId(request.getTemplateId());
    }

    //提交完成
    @PostMapping(value = "/finish")
    public JsonResponse add_record(@RequestBody Equipmentmaintenance_tasksheet_finish request) {

        return equipmentmaintenance_tasksheetService.finish(request);
    }

    //巡检结论
    @PostMapping(value = "/add_itemcontentconclusion_record")
    public JsonResponse add_itemcontentconclusion_record(@RequestBody Equipmentmaintenance_tasksheet_itemcontentconclusion request) {

        return equipmentmaintenance_tasksheetService.add_itemcontentconclusion_record(request);
    }

    //服务事项
    @PostMapping(value = "/add_serviceitem_record")
    public JsonResponse add_serviceitem_record(@RequestBody Equipmentmaintenance_tasksheet_serviceitem request) {

        return equipmentmaintenance_tasksheetService.add_serviceitem_record(request);
    }

    //添加工单状态
    @PostMapping(value = "/add_statetrack_record")
    public JsonResponse add_statetrack_record(@RequestBody Equipmentmaintenance_tasksheet_statetrack request) {

        return equipmentmaintenance_tasksheetService.add_statetrack_record(request);
    }

    //工单进入检验
    @PostMapping(value = "/find1")
    public JsonResponse find1(@RequestBody @Valid search request) {


        return  equipmentmaintenance_tasksheetService.find1(request.getId(),request.getTime());
    }
}

