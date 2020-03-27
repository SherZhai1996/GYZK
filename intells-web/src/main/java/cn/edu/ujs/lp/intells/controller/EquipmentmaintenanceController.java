package cn.edu.ujs.lp.intells.controller;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.request.*;
import cn.edu.ujs.lp.intells.operationmaintenance.response.EquipmentmaintenanceTasksheetResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.service.EquipmentmaintenanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备保养接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/equipmentmaintenance_item")
public class EquipmentmaintenanceController {

    @Autowired
    EquipmentmaintenanceService equipmentmaintenanceService;

    @PostMapping(value = "/insertIntoTemplate")
    @ApiOperation(value = "新增保养项目和保养模板" ,  notes="新增保养项目")
    public Map<String,Object> insertIntoTemplate(@RequestBody EquipmentmaintenanceTemplateRequest request){
        return equipmentmaintenanceService.insertIntoTemplate(request);
    }
    @PostMapping(value = "/selectEquipmentmaintenanceItemByTemplateId")
    @ApiOperation(value = "根据保养模板Id查询保养项目" ,  notes="查询保养项目")
    public Map<String,Object> selectEquipmentmaintenanceItemByTemplateId(@RequestBody EquipmentmaintenanceItemRequest request){
        return equipmentmaintenanceService.selectEquipmentmaintenanceItemByTemplateId(request);
    }
    @PostMapping(value = "/selectEquipmentmaintenanceTemplateByCondition")
    @ApiOperation(value = "查询保养模板" ,  notes="查询保养模板")
    public Map<String,Object> selectEquipmentmaintenanceTemplateByCondition(@RequestBody EquipmentmaintenanceTemplateRequest request){
        return equipmentmaintenanceService.selectEquipmentmaintenanceTemplateByCondition(request);
    }
    @PostMapping(value = "/updateEquipmentmaintenanceTemplateByCondition")
    @ApiOperation(value = "修改保养项目和保养模板" ,  notes="修改保养项目")
    public Map<String,Object> updateEquipmentmaintenanceTemplateByCondition(@RequestBody EquipmentmaintenanceTemplateRequest request){
        return equipmentmaintenanceService.updateEquipmentmaintenanceTemplateByCondition(request);
    }
    @PostMapping(value = "/deleteEquipmentmaintenanceTemplateByCondition")
    @ApiOperation(value = "删除保养项目和保养模板" ,  notes="删除保养项目")
    public Map<String,Object> deleteEquipmentmaintenanceTemplateByCondition(@RequestBody String id){
        return equipmentmaintenanceService.deleteEquipmentmaintenanceTemplateByCondition(id);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenancePlan")
    @ApiOperation(value = "新增保养计划" ,  notes="新增保养计划")
    public Map<String,Object> insertIntoEquipmentmaintenancePlan(@RequestBody EquipmentmaintenancePlanRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentmaintenancePlan(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenancePlanDevice")
    @ApiOperation(value = "新增设备关联表" ,  notes="新增设备关联表")
    public Map<String,Object> insertIntoEquipmentmaintenancePlanDevice(@RequestBody EquipmentmaintenancePlanDeviceRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentmaintenancePlanDevice(request);
    }
    @PostMapping(value = "/deleteEquipmentmaintenancePlan")
    @ApiOperation(value = "删除保养计划" ,  notes="删除保养计划")
    public Map<String,Object> deleteEquipmentmaintenancePlan(@RequestBody EquipmentmaintenancePlanRequest request){
        return equipmentmaintenanceService.deleteEquipmentmaintenancePlan(request);
    }
    @PostMapping(value = "/deleteEquipmentmaintenancePlanDevice")
    @ApiOperation(value = "根据planId删除设备关联表" ,  notes="删除设备关联表")
    public Map<String,Object> deleteEquipmentmaintenancePlanDevice(@RequestBody EquipmentmaintenancePlanDeviceRequest request){
        return equipmentmaintenanceService.deleteEquipmentmaintenancePlanDevice(request);
    }
    @PostMapping(value = "/deleteEquipmentmaintenancePlanDeviceById")
    @ApiOperation(value = "根据Id删除设备关联表" ,  notes="删除设备关联表")
    public Map<String,Object> deleteEquipmentmaintenancePlanDeviceById(@RequestBody EquipmentmaintenancePlanDeviceRequest request){
        return equipmentmaintenanceService.deleteEquipmentmaintenancePlanDeviceById(request);
    }

    @PostMapping(value = "/selectEquipmentmaintenancePlanDeviceByCondition")
    @ApiOperation(value = "查询设备关联表" ,  notes="查询设备关联表")
    public Map<String,Object> selectEquipmentmaintenancePlanDeviceByCondition(@RequestBody EquipmentmaintenancePlanDeviceRequest request){
        return equipmentmaintenanceService.selectEquipmentmaintenancePlanDeviceByCondition(request);
    }
    @PostMapping(value = "/updateEquipmentmaintenancePlan")
    @ApiOperation(value = "修改保养计划" ,  notes="修改保养计划")
    public Map<String,Object> updateEquipmentmaintenancePlan(@RequestBody EquipmentmaintenancePlanRequest request){
        return equipmentmaintenanceService.updateEquipmentmaintenancePlan(request);
    }
    @PostMapping(value = "/selectEquipmentmaintenancePlanByCondition")
    @ApiOperation(value = "查询保养计划" ,  notes="查询保养计划")
    public Map<String,Object> selectEquipmentmaintenancePlanByCondition(@RequestBody EquipmentmaintenancePlanRequest request){
        return equipmentmaintenanceService.selectEquipmentmaintenancePlanByCondition(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByYear")
    @ApiOperation(value = "根据年生成保养工单" ,  notes="根据年生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByYear(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception{
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByYear(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByWeek")
    @ApiOperation(value = "根据周生成保养工单" ,  notes="根据周生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByWeek(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception{
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByWeek(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByHalfMonth")
    @ApiOperation(value = "根据半月生成保养工单" ,  notes="根据半月生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByHalfMonth(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception {
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByHalfMonth(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByMonth")
    @ApiOperation(value = "根据月生成保养工单" ,  notes="根据月生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByMonth(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception{
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByMonth(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByQuarter")
    @ApiOperation(value = "根据季度生成保养工单" ,  notes="根据季度生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByQuarter(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception{
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByQuarter(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetByHalfYear")
    @ApiOperation(value = "根据季度生成保养工单" ,  notes="根据季度生成保养工单")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetByHalfYear(@RequestBody EquipmentmaintenanceTasksheetRequest request)throws Exception{
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetByHalfYear(request);
    }
    @PostMapping(value = "/selectEquipmentmaintenanceTasksheetByCondition")
    @ApiOperation(value = "查询保养工单" ,  notes="查询保养工单")
    public Map<String,Object> selectEquipmentmaintenanceTasksheetByCondition(@RequestBody EquipmentmaintenanceTasksheetResponse request){
        return equipmentmaintenanceService.selectEquipmentmaintenanceTasksheetByCondition(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetUndertake")
    @ApiOperation(value = "生成设备保养工单派工信息表" ,  notes="生成设备保养工单派工信息表")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetUndertake(@RequestBody EquipmentmaintenanceTasksheetUndertakeRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetUndertake(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetStatetrack")
    @ApiOperation(value = "生成设备保养工单状态表" ,  notes="生成设备保养工单状态表")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetStatetrack(@RequestBody EquipmentmaintenanceTasksheetRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetStatetrack(request);
    }
    @PostMapping(value = "/insertIntoEquipmentrepairTasksheetStatetrack")
    @ApiOperation(value = "生成设备维修工单状态表" ,  notes="生成设备维修工单状态表")
    public Map<String,Object> insertIntoEquipmentrepairTasksheetStatetrack(@RequestBody EquipmentrepairTasksheetStatetrackRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentrepairTasksheetStatetrack(request);
    }


    @PostMapping(value = "/selectEquipmentrepairTasksheetByCondition")
    @ApiOperation(value = "查询维修工单" ,  notes="查询维修工单")
    public Map<String,Object> selectEquipmentrepairTasksheetByCondition(@RequestBody EquipmentrepairTasksheetRequest request){
        return equipmentmaintenanceService.selectEquipmentrepairTasksheetByCondition(request);
    }
    @PostMapping(value = "/updateEquipmentrepairTasksheetFinish")
    @ApiOperation(value = "完工时更新工单" ,  notes="完工时更新工单")
    public Map<String,Object> updateEquipmentrepairTasksheetFinish(@RequestBody EquipmentrepairTasksheetRequest request){
        return equipmentmaintenanceService.updateEquipmentrepairTasksheetFinish(request);
    }
    @PostMapping(value = "/insertIntoEquipmentrepairTasksheetUndertake")
    @ApiOperation(value = "设备维修派工" ,  notes="设备维修派工")
    public Map<String,Object> insertIntoEquipmentrepairTasksheetUndertake(@RequestBody EquipmentrepairTasksheetUndertakeRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentrepairTasksheetUndertake(request);
    }
    @PostMapping(value = "/insertIntoEquipmentmaintenanceTasksheetServiceItem")
    @ApiOperation(value = "生成设备保养服务事项" ,  notes="生成设备保养服务事项")
    public Map<String,Object> insertIntoEquipmentmaintenanceTasksheetServiceItem(@RequestBody EquipmentmaintenanceTasksheetServiceitemRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentmaintenanceTasksheetServiceItem(request);
    }
    @PostMapping(value = "/insertIntoEquipmentrepairTasksheetServiceItem")
    @ApiOperation(value = "生成设备维修服务事项" ,  notes="生成设备维修服务事项")
    public Map<String,Object> insertIntoEquipmentrepairTasksheetServiceItem(@RequestBody EquipmentrepairTasksheetServiceItemRequest request){
        return equipmentmaintenanceService.insertIntoEquipmentrepairTasksheetServiceItem(request);
    }
    @PostMapping(value = "/updateEquipmentmaintenanceTasksheetFinish")
    @ApiOperation(value = "完工时更新设备保养工单" ,  notes="完工时更新设备保养工单")
    public Map<String,Object> updateEquipmentmaintenanceTasksheetFinish(@RequestBody EquipmentmaintenanceTasksheetResponse request){
        return equipmentmaintenanceService.updateEquipmentmaintenanceTasksheetFinish(request);
    }
    @PostMapping(value = "/selectGridByDevice")
    @ApiOperation(value = "根据设备id查找grid" ,  notes="根据设备id查找grid")
    public Map<String,Object> selectGridByDevice(@RequestBody @Valid String id){
        return equipmentmaintenanceService.selectGridByDevice(id);
    }


    @ApiOperation(value = "上传设备保养图片",notes = "上传设备保养图片")
    @ResponseBody
    @PostMapping(value = "/uploadPic")
    public JsonResponse uploadPic (HttpServletRequest request){
        return equipmentmaintenanceService.uploadPic(request);
    }

    @ApiOperation(value = "删除设备保养图片",notes = "删除设备保养图片")
    @ResponseBody
    @PostMapping(value = "/clearUploadPic")
    public JsonResponse clearUploadPic (@RequestBody @Valid String id){
        return equipmentmaintenanceService.clearUploadPic(id);
    }
    @ApiOperation(value = "上传设备保养音频",notes = "上传设备保养音频")
    @ResponseBody
    @PostMapping(value = "/uploadWav")
    public JsonResponse uploadWav (HttpServletRequest request){
        return equipmentmaintenanceService.uploadWav(request);
    }

    @ApiOperation(value = "删除设备保养音频",notes = "删除设备保养音频")
    @ResponseBody
    @PostMapping(value = "/clearUploadWav")
    public JsonResponse clearUploadWav (@RequestBody @Valid String id){
        return equipmentmaintenanceService.clearUploadPic(id);
    }

    @ApiOperation(value = "上传设备维修图片",notes = "上传设备维修图片")
    @ResponseBody
    @PostMapping(value = "/uploadPicRepair")
    public JsonResponse uploadPicRepair (HttpServletRequest request){
        return equipmentmaintenanceService.uploadPicRepair(request);
    }

    @ApiOperation(value = "删除设备维修图片",notes = "删除设备维修图片")
    @ResponseBody
    @PostMapping(value = "/clearUploadPicRepair")
    public JsonResponse clearUploadPicRepair (@RequestBody @Valid String id){
        return equipmentmaintenanceService.clearUploadPicRepair(id);
    }
    @ApiOperation(value = "上传设备维修音频",notes = "上传设备维修音频")
    @ResponseBody
    @PostMapping(value = "/uploadWavRepair")
    public JsonResponse uploadWavRepair (HttpServletRequest request){
        return equipmentmaintenanceService.uploadWavRepair(request);
    }

    @ApiOperation(value = "删除设备维修音频",notes = "删除设备维修音频")
    @ResponseBody
    @PostMapping(value = "/clearUploadWavRepair")
    public JsonResponse clearUploadWavRepair (@RequestBody @Valid String id){
        return equipmentmaintenanceService.clearUploadWavRepair(id);
    }
}
