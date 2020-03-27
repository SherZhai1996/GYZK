package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.*;
import cn.edu.ujs.lp.intells.appservice.service.Cleaninginspection_tasksheetService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 保洁巡检工单提交
 *
 * @author sher
 * @date 2019-10-02
 */


@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection_tasksheet")
public class APP_Cleaninginspection_tasksheetController {

    @Autowired
    private Cleaninginspection_tasksheetService cleaninginspection_tasksheetService;

    //增加派工记录
    @PostMapping(value = "/undertake")
    public JsonResponse undertake(@RequestBody Inspection_tasksheet_undertake inspection_tasksheet_undertake) {

        return cleaninginspection_tasksheetService.undertake(inspection_tasksheet_undertake);
    }

    //增加单个项目结论
    @PostMapping(value = "/itemconclusion")
    public JsonResponse Cleaninginspection_tasksheet_itemconclusion(@RequestBody Cleaninginspection_tasksheet_itemconclusion cleaninginspection_tasksheet_itemconclusion) {

        return cleaninginspection_tasksheetService.add_cleaninginspection_tasksheet_itemconclusion(cleaninginspection_tasksheet_itemconclusion);
    }

    //提交工单
    @PostMapping(value = "/finish")
    public JsonResponse finish(@RequestBody tb_cleaninginspection_tasksheet tbcleaninginspection_tasksheet) {

        return cleaninginspection_tasksheetService.finish(tbcleaninginspection_tasksheet);
    }

    //增加工单超时说明
    @PostMapping(value = "/Timeout_instructions")
    public JsonResponse Timeout_instructions(@RequestBody search request) {

        return cleaninginspection_tasksheetService.Timeout_instructions(request.getContent(),request.getEndTime(),request.getHospID());
    }

   //增加状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Cleaninginspection_tasksheet_statetrack cleaninginspection_tasksheet_statetrack) {

        return cleaninginspection_tasksheetService.add_statetrack_Record(cleaninginspection_tasksheet_statetrack);
    }

    //增加服务明细记录
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Cleaninginspection_tasksheet_serviceitem cleaninginspection_tasksheet_serviceitem) {

        return cleaninginspection_tasksheetService.add_serviceitem_Record(cleaninginspection_tasksheet_serviceitem);
    }

    //查找班组员工 冗余
    @PostMapping(value = "/find_cleaninginspection_person")
    public JsonResponse find_by_Hosp_ID(@RequestBody @Valid search request){
        return cleaninginspection_tasksheetService.find_cleaninginspection_person(request.getId());
    }

    //员工查询
    @PostMapping(value = "/find_by_exstaff")
    public JsonResponse find_by_exstaff(@RequestBody @Valid search request) {


        return  cleaninginspection_tasksheetService.find_by_exstaff(request.getPerson(),request.getSheetstate());
    }

    //班组长查询
    @PostMapping(value = "/find_by_GM")
    public JsonResponse find_by_GM(@RequestBody @Valid search request) {


        return  cleaninginspection_tasksheetService. find_by_GM(request.getPerson(),request.getSheetstate());
    }

    //主管查询
    @PostMapping(value = "/find_by_mangener")
    public JsonResponse find_by_mangener(@RequestBody @Valid search request) {


        return  cleaninginspection_tasksheetService.find_by_mangener(request.getSheetstate());
    }


    //工单进入检验
    @PostMapping(value = "/find1")
    public JsonResponse find1(@RequestBody @Valid search request) {


        return  cleaninginspection_tasksheetService.find1(request.getId(),request.getTime(), request.getIspatrol());
    }
}
