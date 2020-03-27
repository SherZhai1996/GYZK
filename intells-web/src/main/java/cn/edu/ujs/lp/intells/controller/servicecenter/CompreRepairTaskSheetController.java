package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.CleaingTaskSheet.CleaningExportDetialPdfRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.CleaingTaskSheet.CleaningExportPagePdfRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ComprerepairTaskSheet.*;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.DetpToLocalRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocaTypeToExstaffRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocalToDetpRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.PhoneToMoreRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.CompreRepairTaskSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 工单维修服务接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "工单维修接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/task")
public class CompreRepairTaskSheetController {

    @Autowired
    private CompreRepairTaskSheetService compreRepairTaskSheetService;

    @ApiOperation(value = "创建",notes = "创建")
    @PostMapping(value = "/create")
    @ResponseBody
    public JsonResponse1 create(@RequestBody @Valid  CompreRepairTaskSheetCreateRequest request,HttpServletResponse response)throws Exception {
        compreRepairTaskSheetService.create(request,response);
        return JsonResponse1.success("创建成功");
    }

    @ApiOperation(value = "派工",notes = "派工")
    @PostMapping(value = "/undertake")
    public JsonResponse1 undertake(@RequestBody @Valid CompreRepairTaskSheetUndertakeRequest request) {
        compreRepairTaskSheetService.undertake(request);
        return JsonResponse1.success("派工成功");
    }

    @ApiOperation(value = "受理",notes = "受理")
    @PostMapping(value = "/receipt")
    public JsonResponse1 receipt(@RequestBody @Valid CompreRepairTaskSheetReceiptRequest request) {
        compreRepairTaskSheetService.receipt(request);
        return JsonResponse1.success("受理成功");
    }

    @ApiOperation(value = "耗材登记",notes = "耗材登记")
    @PostMapping(value = "/registration")
    public JsonResponse1 registration(@RequestBody @Valid CompreRapairTaskSheetRegistrationRequest request) {
        compreRepairTaskSheetService.registration(request);
        return JsonResponse1.success("耗材登记成功");
    }

    @ApiOperation(value = "完成",notes = "完成")
    @PostMapping(value = "/finish")
    public JsonResponse1 finish(@RequestBody @Valid CompreRepairTaskSheetFinishRequest request) {
        compreRepairTaskSheetService.finish(request);
        return JsonResponse1.success("完成成功");
    }

    @ApiOperation(value = "督办",notes = "督办")
    @PostMapping(value = "/supervise")
    public JsonResponse1 supervise(@RequestBody @Valid CompreRepairTaskSheetSuperviseRequest request) {
        compreRepairTaskSheetService.supervise(request);
        return JsonResponse1.success("督办成功");
    }

    @ApiOperation(value = "评价",notes = "评价")
    @PostMapping(value = "/assess")
    public JsonResponse1 assess(@RequestBody @Valid CompreRepairTaskSheetAssessRequest request) {
        compreRepairTaskSheetService.assess(request);
        return JsonResponse1.success("评价成功");
    }

    @ApiOperation(value = "回访",notes = "回访")
    @PostMapping(value = "/returnvisit")
    public JsonResponse1 returnVisit(@RequestBody @Valid CompreRepairTaskSheetReturnVisitRequest request) {
        compreRepairTaskSheetService.returnVisit(request);
        return JsonResponse1.success("回访成功");
    }

    @ApiOperation(value = "投诉",notes = "投诉")
    @PostMapping(value = "/complain")
    public JsonResponse1 complain(@RequestBody @Valid CompreRepairTaskSheetComplainRequest request)throws Exception {
        compreRepairTaskSheetService.complain(request);
        return JsonResponse1.success("投诉成功");
    }

    @ApiOperation(value = "返修",notes = "返修")
    @PostMapping(value = "/rework")
    public JsonResponse1 rework(@RequestBody @Valid IdRequest request) {
        compreRepairTaskSheetService.rework(request);
        return JsonResponse1.success("返修成功");
    }
    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return compreRepairTaskSheetService.init(request);
    }

    @ApiOperation(value = "查询所有",notes = "查询所有")
    @PostMapping(value = "/queryAll")
    public JsonResponse1 queryAll() {
        return compreRepairTaskSheetService.queryAll();
    }

    @ApiOperation(value = "根据电话查",notes = "根据电话查")
    @PostMapping(value = "/phoneToMore")
    public JsonResponse1 phoneToMore(@RequestBody @Valid PhoneToMoreRequest request)
    {
        if(compreRepairTaskSheetService.phoneToMore(request)==null)
            return JsonResponse1.fail(1,"未查到无消息");
        return JsonResponse1.success(compreRepairTaskSheetService.phoneToMore(request));
    }

    @ApiOperation(value = "获取时间",notes = "获取时间")
    @PostMapping(value = "/getTime")
    public JsonResponse1 getTime(@RequestBody @Valid IdRequest request) {
        return compreRepairTaskSheetService.getTime(request);
    }

    @ApiOperation(value = "删除",notes = "删除")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        compreRepairTaskSheetService.delete(request);
        return JsonResponse1.success("删除成功");
    }

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid CompreRapairTaskSheetQueryRequest request) {
        return compreRepairTaskSheetService.queryByPage(request);
    }

    @ApiOperation(value = "地点过滤科室",notes = "地点过滤科室")
    @PostMapping(value = "/locaToDept")
    public JsonResponse1 locaToDept(@RequestBody @Valid LocalToDetpRequest request) {
        return compreRepairTaskSheetService.locaToDept(request);
    }

    @ApiOperation(value = "科室过滤地点",notes = "科室过滤地点")
    @PostMapping(value = "/deptToLoca")
    public JsonResponse1 deptToLoca(@RequestBody @Valid DetpToLocalRequest request) {
        return compreRepairTaskSheetService.deptToLoca(request);
    }

    @ApiOperation(value = "过滤上班人员",notes = "过滤上班人员")
    @PostMapping(value = "/locaTypeToExstaff")
    public JsonResponse1 locaTypeToExstaff(@RequestBody @Valid LocaTypeToExstaffRequest request) {
        return compreRepairTaskSheetService.locaTypeToExstaff(request);
    }

    @ApiOperation(value = "id过滤状态",notes = "id过滤状态")
    @PostMapping(value = "/idToState")
    public JsonResponse1 idToState(@RequestBody @Valid IdRequest request) {
        return compreRepairTaskSheetService.idToState(request);
    }

    @ApiOperation(value = "打印pdfpage",notes = "打印pdfpage")
    @PostMapping(value = "/exportPagePdf")
    @ResponseBody
    public JsonResponse exportPagePdf(@RequestBody @Valid CleaningExportPagePdfRequest request, HttpServletResponse response) throws Exception{
        return compreRepairTaskSheetService.exportPagePdf(request,response);
    }

    @ApiOperation(value = "打印pdfdetial",notes = "打印pdfdetial")
    @PostMapping(value = "/exportDetailsPdf")
    @ResponseBody
    public JsonResponse exportDetailsPdf(@RequestBody @Valid CleaningExportDetialPdfRequest request, HttpServletResponse response) throws Exception{
        return compreRepairTaskSheetService.exportDetailsPdf(request,response);
    }
}