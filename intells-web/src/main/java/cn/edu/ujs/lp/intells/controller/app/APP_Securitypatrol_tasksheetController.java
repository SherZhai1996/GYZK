package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.appservice.entity.*;
import cn.edu.ujs.lp.intells.appservice.service.Cleaninginspection_tasksheetService;
import cn.edu.ujs.lp.intells.appservice.service.Securitypatrol_tasksheetService;
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
@RequestMapping(value = "/Securitypatrol_tasksheet")
public class APP_Securitypatrol_tasksheetController {


    @Autowired
    private Securitypatrol_tasksheetService securitypatrol_tasksheetService;




    //提交工单
    @PostMapping(value = "/finish")
    public JsonResponse finish(@RequestBody Securitypatrol_tasksheet securitypatrol_tasksheet) {

        return securitypatrol_tasksheetService.finish(securitypatrol_tasksheet);
    }

    //增加工单超时说明
    @PostMapping(value = "/Timeout_instructions")
    public JsonResponse Timeout_instructions(@RequestBody search request) {

        return securitypatrol_tasksheetService.Timeout_instructions(request.getContent(),request.getEndTime(),request.getHospID());
    }

    //增加状态记录
    @PostMapping(value = "/add_statetrack_Record")
    public JsonResponse add_statetrack_Record(@RequestBody Securitypatrol_tasksheet_statetrack securitypatrol_tasksheet_statetrack) {

        return securitypatrol_tasksheetService.add_statetrack_Record(securitypatrol_tasksheet_statetrack);
    }

    //增加服务明细记录
    @PostMapping(value = "/add_serviceitem_Record")
    public JsonResponse add_serviceitem_Record(@RequestBody Securitypatrol_tasksheet_serviceitem securitypatrol_tasksheet_serviceitem) {

        return securitypatrol_tasksheetService.add_serviceitem_Record(securitypatrol_tasksheet_serviceitem);
    }

    //查找班组员工
    @PostMapping(value = "/find_securitypatrol_person")
    public JsonResponse find_securitypatrol_person(@RequestBody @Valid search request) {
        return securitypatrol_tasksheetService.find_securitypatrol_person(request.getId());
    }


    //队员工单进入检验
    @PostMapping(value = "/find1")
    public JsonResponse find1(@RequestBody @Valid search request) {


        return securitypatrol_tasksheetService.find1(request.getId(), request.getTime(), request.getIspatrol());
    }

    //队长工单进入检验
    @PostMapping(value = "/find2")
    public JsonResponse find2(@RequestBody @Valid search request) {


        return securitypatrol_tasksheetService.find2(request.getId(), request.getTime());
    }


    //通过项目id查找项目内容
    @PostMapping(value = "/findByItemId")
    public JsonResponse find_by_templateId(@RequestBody @Valid Securitypatrol_itemcontent request) {
        return securitypatrol_tasksheetService.find_by_itemId(request.getItemId());
    }

    //通过模板id号查找项目
    @PostMapping(value = "/find_by_templateId")
    public JsonResponse find_by_templateId(@RequestBody @Valid Securitypatrol_item request) {
        return securitypatrol_tasksheetService.find_by_templateId(request.getTemplateId());
    }


    //增加单个项目结论
    @PostMapping(value = "/itemconclusion")
    public JsonResponse Cleaninginspection_tasksheet_itemconclusion(@RequestBody Securitypatrol_tasksheet_itemconclusion securitypatrol_tasksheet_itemconclusion) {

        return securitypatrol_tasksheetService.add_cleaninginspection_tasksheet_itemconclusion(securitypatrol_tasksheet_itemconclusion);
    }

}