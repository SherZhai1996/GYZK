package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.CleaingTaskSheet.*;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.DetpToLocalRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocaTypeToExstaffRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocalToDetpRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.PhoneToMoreRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.CleaningTaskSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 工单保洁服务接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "工单保洁接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/clean")
public class CleaningTaskSheetController {

    @Autowired
    private CleaningTaskSheetService cleaningTaskSheetService;

    @ApiOperation(value = "创建",notes = "创建")
    @PostMapping(value = "/create")
    @ResponseBody
    public JsonResponse1 create(@RequestBody @Valid CleaningTaskSheetCreateRequest request,HttpServletResponse response)throws Exception {
        cleaningTaskSheetService.create(request,response);
        return JsonResponse1.success("创建成功");
    }

    @ApiOperation(value = "派工",notes = "派工")
    @PostMapping(value = "/undertake")
    public JsonResponse1 undertake(@RequestBody @Valid CleaningTaskSheetUndertakeRequest request) {
        cleaningTaskSheetService.undertake(request);
        return JsonResponse1.success("派工成功");
    }

    @ApiOperation(value = "受理",notes = "受理")
    @PostMapping(value = "/receipt")
    public JsonResponse1 receipt(@RequestBody @Valid CleaningTaskSheetReceiptRequest request) {
        cleaningTaskSheetService.receipt(request);
        return JsonResponse1.success("受理成功");
    }

    @ApiOperation(value = "耗材登记",notes = "耗材登记")
    @PostMapping(value = "/registration")
    public JsonResponse1 registration(@RequestBody @Valid CleaningTaskSheetRegistrationRequest request) {
        cleaningTaskSheetService.registration(request);
        return JsonResponse1.success("耗材登记成功");
    }

    @ApiOperation(value = "完成",notes = "完成")
    @PostMapping(value = "/finish")
    public JsonResponse1 finish(@RequestBody @Valid CleaningTaskSheetFinishRequest request) {
        cleaningTaskSheetService.finish(request);
        return JsonResponse1.success("成功完成");
    }

    @ApiOperation(value = "督办",notes = "督办")
    @PostMapping(value = "/supervise")
    public JsonResponse1 supervise(@RequestBody @Valid CleaningTaskSheetSuperviseRequest request) {
        cleaningTaskSheetService.supervise(request);
        return JsonResponse1.success("督办成功");
    }

    @ApiOperation(value = "评价",notes = "评价")
    @PostMapping(value = "/assess")
    public JsonResponse1 assess(@RequestBody @Valid CleaningTaskSheetAssessRequest request) {
        cleaningTaskSheetService.assess(request);
        return JsonResponse1.success("评价成功");
    }

    @ApiOperation(value = "回访",notes = "回访")
    @PostMapping(value = "/returnvisit")
    public JsonResponse1 returnVisit(@RequestBody @Valid CleaningTaskSheetReturnVisitRequest request) {
        cleaningTaskSheetService.returnVisit(request);
        return JsonResponse1.success("回访成功");
    }

    @ApiOperation(value = "投诉",notes = "投诉")
    @PostMapping(value = "/complain")
    public JsonResponse1 complain(@RequestBody @Valid CleaingTaskSheetComplainRequest request)throws Exception {
        cleaningTaskSheetService.complain(request);
        return JsonResponse1.success("投诉成功");
    }

    @ApiOperation(value = "返修",notes = "返修")
    @PostMapping(value = "/rework")
    public JsonResponse1 rework(@RequestBody @Valid IdRequest request) {
        cleaningTaskSheetService.rework(request);
        return JsonResponse1.success("返修成功");
    }
    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return cleaningTaskSheetService.init(request);
    }

    @ApiOperation(value = "查询所有",notes = "查询所有")
    @PostMapping(value = "/queryAll")
    public JsonResponse1 queryAll() {
        return cleaningTaskSheetService.queryAll();
    }

    @ApiOperation(value = "根据电话查",notes = "根据电话查")
    @PostMapping(value = "/phoneToMore")
    public JsonResponse1 phoneToMore(@RequestBody @Valid PhoneToMoreRequest request)
    {
        if(cleaningTaskSheetService.phoneToMore(request)==null)
            return JsonResponse1.fail(1,"未查到无消息");
        return JsonResponse1.success(cleaningTaskSheetService.phoneToMore(request));
    }

    @ApiOperation(value = "获取时间",notes = "获取时间")
    @PostMapping(value = "/getTime")
    public JsonResponse1 getTime(@RequestBody @Valid IdRequest request) {
        return cleaningTaskSheetService.getTime(request);
    }

    @ApiOperation(value = "删除",notes = "删除")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        cleaningTaskSheetService.delete(request);
        return JsonResponse1.success("删除成功");
    }

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid CleaningTaskSheetQueryRequest request) {
        return cleaningTaskSheetService.queryByPage(request);
    }

    @ApiOperation(value = "地点过滤科室",notes = "地点过滤科室")
    @PostMapping(value = "/locaToDept")
    public JsonResponse1 locaToDept(@RequestBody @Valid LocalToDetpRequest request) {
        return cleaningTaskSheetService.locaToDept(request);
    }

    @ApiOperation(value = "科室过滤地点",notes = "科室过滤地点")
    @PostMapping(value = "/deptToLoca")
    public JsonResponse1 deptToLoca(@RequestBody @Valid DetpToLocalRequest request) {
        return cleaningTaskSheetService.deptToLoca(request);
    }

    @ApiOperation(value = "过滤上班人员",notes = "过滤上班人员")
    @PostMapping(value = "/locaTypeToExstaff")
    public JsonResponse1 locaTypeToExstaff(@RequestBody @Valid LocaTypeToExstaffRequest request) {
        return cleaningTaskSheetService.locaTypeToExstaff(request);
    }

    @ApiOperation(value = "id过滤状态",notes = "id过滤状态")
    @PostMapping(value = "/idToState")
    public JsonResponse1 idToState(@RequestBody @Valid IdRequest request) {
        return cleaningTaskSheetService.idToState(request);
    }

    @ApiOperation(value = "打印pdfpage",notes = "打印pdfpage")
    @PostMapping(value = "/exportPagePdf")
    @ResponseBody
    public JsonResponse exportPagePdf(@RequestBody @Valid CleaningExportPagePdfRequest request,HttpServletResponse response) throws Exception{
        return cleaningTaskSheetService.exportPagePdf(request,response);
    }

    @ApiOperation(value = "打印pdfdetial",notes = "打印pdfdetial")
    @PostMapping(value = "/exportDetailsPdf")
    @ResponseBody
    public JsonResponse exportDetailsPdf(@RequestBody @Valid CleaningExportDetialPdfRequest request,HttpServletResponse response) throws Exception{
        return cleaningTaskSheetService.exportDetailsPdf(request,response);
    }
}