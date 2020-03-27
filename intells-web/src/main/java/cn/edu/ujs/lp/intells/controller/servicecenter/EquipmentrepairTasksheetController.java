package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;

import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.EquipmentRepairTasksheet.*;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.DetpToLocalRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocaTypeToExstaffRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.LocalToDetpRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.ToMore.PhoneToMoreRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.EquipmentrepairTasksheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 工单保洁服务接口
 *
 * @author troy
 * @date 2019-10-05
 */
@Api(description = "设备维修接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/equipment")
public class EquipmentrepairTasksheetController {

    @Autowired
    private EquipmentrepairTasksheetService equipmentrepairTasksheetService;

    @ApiOperation(value = "创建",notes = "创建")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid EquipmentRepairTasksheetCreateRequest request)throws Exception {
        equipmentrepairTasksheetService.create(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "初始化",notes = "初始化")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return equipmentrepairTasksheetService.init(request);
    }

    @ApiOperation(value = "动态查询",notes = "动态查询")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid EquipmentRapairTaskSheetQueryRequest request) {
        return equipmentrepairTasksheetService.queryByPage(request);
    }

    @ApiOperation(value = "派工",notes = "派工")
    @PostMapping(value = "/undertake")
    public JsonResponse1 undertake(@RequestBody @Valid EquipmentRepairTaskSheetUndertakeRequest request) {
        equipmentrepairTasksheetService.undertake(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "受理",notes = "受理")
    @PostMapping(value = "/receipt")
    public JsonResponse1 receipt(@RequestBody @Valid EquipmentRepairTaskSheetReceiptRequest request) {
        equipmentrepairTasksheetService.receipt(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "耗材登记",notes = "耗材登记")
    @PostMapping(value = "/registration")
    public JsonResponse1 registration(@RequestBody @Valid EquipmentRapairTaskSheetRegistrationRequest request) {
        equipmentrepairTasksheetService.registration(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "督办",notes = "督办")
    @PostMapping(value = "/supervise")
    public JsonResponse1 supervise(@RequestBody @Valid EquipmentRepairTaskSheetSuperviseRequest request) {
        equipmentrepairTasksheetService.supervise(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "工单评价",notes = "工单评价")
    @PostMapping(value = "/assess")
    public JsonResponse1 assess(@RequestBody @Valid EquipmentRepairTaskSheetAssessRequest request) {
        equipmentrepairTasksheetService.assess(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "工单回访",notes = "工单回访")
    @PostMapping(value = "/returnVisit")
    public JsonResponse1 returnVisit(@RequestBody @Valid EquipmentRepairTaskSheetReturnVisitRequest request) {
        equipmentrepairTasksheetService.returnVisit(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "投诉",notes = "投诉")
    @PostMapping(value = "/complain")
    public JsonResponse1 complain(@RequestBody @Valid EquipmentRepairTaskSheetComplainRequest request) throws Exception{
        equipmentrepairTasksheetService.complain(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除",notes = "删除")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        equipmentrepairTasksheetService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "查询所有",notes = "查询所有")
    @PostMapping(value = "/queryAll")
    public JsonResponse1 queryAll() {
        return JsonResponse1.success(equipmentrepairTasksheetService.queryAll());
    }

    @ApiOperation(value = "地点查科室",notes = "地点查科室")
    @PostMapping(value = "/locaToDept")
    public JsonResponse1 locaToDept(@RequestBody @Valid LocalToDetpRequest request) {
        return JsonResponse1.success(equipmentrepairTasksheetService.locaToDept(request));
    }

    @ApiOperation(value = "电话查相关信息",notes = "电话查相关信息")
    @PostMapping(value = "/phoneToMore")
    public JsonResponse1 phoneToMore(@RequestBody @Valid PhoneToMoreRequest request) {
        return JsonResponse1.success(equipmentrepairTasksheetService.phoneToMore(request));
    }

    @ApiOperation(value = "查询时间",notes = "查询时间")
    @PostMapping(value = "/getTime")
    public JsonResponse1 getTime(@RequestBody @Valid IdRequest request) {
        return JsonResponse1.success(equipmentrepairTasksheetService.getTime(request));
    }

    @ApiOperation(value = "部门查地点",notes = "部门查地点")
    @PostMapping(value = "/deptToLoca")
    public JsonResponse1 deptToLoca(@RequestBody @Valid DetpToLocalRequest request) {
        return equipmentrepairTasksheetService.deptToLoca(request);
    }

    @ApiOperation(value = "过滤上班人员",notes = "过滤上班人员")
    @PostMapping(value = "/locaTypeToExstaff")
    public JsonResponse1 locaTypeToExstaff(@RequestBody @Valid LocaTypeToExstaffRequest request) {
        return equipmentrepairTasksheetService.locaTypeToExstaff(request);
    }

    @ApiOperation(value = "id过滤状态",notes = "id过滤状态")
    @PostMapping(value = "/idToState")
    public JsonResponse1 idToState(@RequestBody @Valid IdRequest request) {
        return equipmentrepairTasksheetService.idToState(request);
    }
}