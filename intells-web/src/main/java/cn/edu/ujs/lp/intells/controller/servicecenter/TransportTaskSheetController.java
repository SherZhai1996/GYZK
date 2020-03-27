package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocaTypeToExstaffRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.TransportTaskSheet.*;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.DetpToLocalRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocalToDetpRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.PhoneToMoreRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.TransportTaskSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 工单维修服务接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "工单运送接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/transport")
public class TransportTaskSheetController {

    @Autowired
    private TransportTaskSheetService transportTaskSheetService;

    @ApiOperation(value = "创建",notes = "创建")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid TransportTaskSheetCreateRequest request)throws Exception {
        transportTaskSheetService.create(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "派工",notes = "派工")
    @PostMapping(value = "/undertake")
    public JsonResponse1 undertake(@RequestBody @Valid TransportTaskSheetUndertakeRequest request) {
        transportTaskSheetService.undertake(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "受理",notes = "受理")
    @PostMapping(value = "/receipt")
    public JsonResponse1 receipt(@RequestBody @Valid TransportTaskSheetReceiptRequest request) {
        transportTaskSheetService.receipt(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "耗材登记",notes = "耗材登记")
    @PostMapping(value = "/registration")
    public JsonResponse1 registration(@RequestBody @Valid TransportTaskSheetRegistrationRequest request) {
        transportTaskSheetService.registration(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "完成",notes = "完成")
    @PostMapping(value = "/finish")
    public JsonResponse1 finish(@RequestBody @Valid TransportTaskSheetFinishRequest request) {
        transportTaskSheetService.finish(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "督办",notes = "督办")
    @PostMapping(value = "/supervise")
    public JsonResponse1 supervise(@RequestBody @Valid TransportTaskSheetSuperviseRequest request) {
        transportTaskSheetService.supervise(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "评价",notes = "评价")
    @PostMapping(value = "/assess")
    public JsonResponse1 assess(@RequestBody @Valid TransportTaskSheetAssessRequest request) {
        transportTaskSheetService.assess(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "回访",notes = "回访")
    @PostMapping(value = "/returnvisit")
    public JsonResponse1 returnVisit(@RequestBody @Valid TransportTaskSheetReturnVisitRequest request) {
        transportTaskSheetService.returnVisit(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "投诉",notes = "投诉")
    @PostMapping(value = "/complain")
    public JsonResponse1 complain(@RequestBody @Valid TransportTaskSheetComplainRequest request)throws Exception {
        transportTaskSheetService.complain(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "返修",notes = "返修")
    @PostMapping(value = "/rework")
    public JsonResponse1 rework(@RequestBody @Valid IdRequest request) {
        transportTaskSheetService.rework(request);
        return JsonResponse1.success();
    }
    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return transportTaskSheetService.init(request);
    }

    @ApiOperation(value = "查询所有",notes = "查询所有")
    @PostMapping(value = "/queryAll")
    public JsonResponse1 queryAll() {
        return transportTaskSheetService.queryAll();
    }

    @ApiOperation(value = "根据电话查",notes = "根据电话查")
    @PostMapping(value = "/phoneToMore")
    public JsonResponse1 phoneToMore(@RequestBody @Valid PhoneToMoreRequest request)
    {
        if(transportTaskSheetService.phoneToMore(request)==null)
            return JsonResponse1.fail(1,"未查到无消息");
        return JsonResponse1.success(transportTaskSheetService.phoneToMore(request));
    }

    @ApiOperation(value = "获取时间",notes = "获取时间")
    @PostMapping(value = "/getTime")
    public JsonResponse1 getTime(@RequestBody @Valid IdRequest request) {
        return transportTaskSheetService.getTime(request);
    }

    @ApiOperation(value = "删除",notes = "删除")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        transportTaskSheetService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid TransportTaskSheetQueryRequest request) {
        return transportTaskSheetService.queryByPage(request);
    }

    @ApiOperation(value = "地点过滤科室",notes = "地点过滤科室")
    @PostMapping(value = "/locaToDept")
    public JsonResponse1 locaToDept(@RequestBody @Valid LocalToDetpRequest request) {
        return transportTaskSheetService.locaToDept(request);
    }

    @ApiOperation(value = "科室过滤地点",notes = "科室过滤地点")
    @PostMapping(value = "/deptToLoca")
    public JsonResponse1 deptToLoca(@RequestBody @Valid DetpToLocalRequest request) {
        return transportTaskSheetService.deptToLoca(request);
    }

    @ApiOperation(value = "过滤上班人员",notes = "过滤上班人员")
    @PostMapping(value = "/locaTypeToExstaff")
    public JsonResponse1 locaTypeToExstaff(@RequestBody @Valid LocaTypeToExstaffRequest request) {
        return transportTaskSheetService.locaTypeToExstaff(request);
    }

    @ApiOperation(value = "id过滤状态",notes = "id过滤状态")
    @PostMapping(value = "/idToState")
    public JsonResponse1 idToState(@RequestBody @Valid IdRequest request) {
        return transportTaskSheetService.idToState(request);
    }
}