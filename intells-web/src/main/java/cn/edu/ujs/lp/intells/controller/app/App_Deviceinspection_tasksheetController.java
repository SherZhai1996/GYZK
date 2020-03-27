package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_itemradioitem;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_tasksheet_serviceitem;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_tasksheet_statetrack;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_tasksheet_undertake;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_tasksheet_finish;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_tasksheet_itemcontentconclusion;
import cn.edu.ujs.lp.intells.appservice.entity.Equipmentmaintenance_tasksheet;
import cn.edu.ujs.lp.intells.appservice.entity.search;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_item;
import cn.edu.ujs.lp.intells.appservice.entity.Deviceinspection_itemcontent;
import cn.edu.ujs.lp.intells.appservice.entity.Cleaning_tasksheet_Appsearchindex;
import cn.edu.ujs.lp.intells.appservice.service.Deviceinspection_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 设备巡检接口
 *
 * @author sher
 * @date 2019-10-23
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection_tasksheet")
public class App_Deviceinspection_tasksheetController {

    @Autowired
    private Deviceinspection_tasksheetService deviceinspection_tasksheetService;

    //查询主管
    @PostMapping(value = "/findByrole")
    public JsonResponse findByrole() {


        return  deviceinspection_tasksheetService.findByrole();
    }

    //增加工单超时说明
    @PostMapping(value = "/Timeout_instructions")
    public JsonResponse Timeout_instructions(@RequestBody search request) {

        return deviceinspection_tasksheetService.Timeout_instructions(request.getContent(),request.getEndTime(),request.getHospID());
    }

    //派工
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Deviceinspection_tasksheet_undertake deviceinspection_tasksheet_undertake) {

        return deviceinspection_tasksheetService.undertake(deviceinspection_tasksheet_undertake);
    }

    //增加状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Deviceinspection_tasksheet_statetrack deviceinspection_tasksheet_statetrack) {

        return deviceinspection_tasksheetService.add_statetrack_Record(deviceinspection_tasksheet_statetrack);
    }

    //增加服务事项记录
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Deviceinspection_tasksheet_serviceitem deviceinspection_tasksheet_serviceitem) {

        return deviceinspection_tasksheetService.add_serviceitem_Record(deviceinspection_tasksheet_serviceitem);
    }

    //检验是否能进入工单
    @PostMapping(value = "/find_by_time")
    public JsonResponse find_by_time(@RequestBody @Valid search request) {

        return deviceinspection_tasksheetService.find1(request.getId(),request.getTime(),request.getIspatrol());
    }

    //员工 设备巡检查询
    @PostMapping(value = "/find_by_exstaffId")
    public JsonResponse find_by_exstaffId(@RequestBody @Valid search request) {


        return  deviceinspection_tasksheetService.find_by_exstaffId(request.getPerson(),request.getSheetstate());
    }

    //员工 设备巡检查询
    @PostMapping(value = "/find_by_GM")
    public JsonResponse find_by_GM(@RequestBody @Valid search request) {


        return  deviceinspection_tasksheetService.find_by_GM(request.getPerson(),request.getSheetstate());
    }

    //主管 设备巡检查询
    @PostMapping(value = "/find_by_manager")
    public JsonResponse find_by_manager(@RequestBody @Valid search request) {


        return  deviceinspection_tasksheetService.find_by_manager(request.getSheetstate());
    }

    //员工 实时工单查询 废弃
    @PostMapping(value = "/find_item_content")
    public JsonResponse find_item_content(@RequestBody @Valid search request) {


        return  deviceinspection_tasksheetService.find_item_content(request.getId(),request.getPerson(),request.getTime());
    }

    //员工 工单项目查询
    @PostMapping(value = "/findByTemplateId")
    public JsonResponse findByTemplateId(@RequestBody @Valid Deviceinspection_item request) {


        return  deviceinspection_tasksheetService.findByTemplateId(request.getTemplateId());
    }

    //员工 工单项目id查询内容
    @PostMapping(value = "/findByItemId")
    public JsonResponse findByItemId(@RequestBody @Valid Deviceinspection_itemcontent request) {


        return  deviceinspection_tasksheetService.findByItemId(request.getItemId());
    }

    //员工 工单项目内容id查询
    @PostMapping(value = "/findByItemcontentId")
    public JsonResponse findByItemId(@RequestBody @Valid Deviceinspection_itemradioitem request) {


        return  deviceinspection_tasksheetService.findByItemcontentId(request.getItemcontentId());
    }

    //提交完成
    @PostMapping(value = "/add_record")
    public JsonResponse add_record(@RequestBody Deviceinspection_tasksheet_finish request) {

        return deviceinspection_tasksheetService.add_record(request);
    }

    //单个项目结论
    @PostMapping(value = "/add_itemcontentconclusion_record")
    public JsonResponse add_itemcontentconclusion_record(@RequestBody Deviceinspection_tasksheet_itemcontentconclusion request) {

        return deviceinspection_tasksheetService.add_itemcontentconclusion_record(request);
    }
}

