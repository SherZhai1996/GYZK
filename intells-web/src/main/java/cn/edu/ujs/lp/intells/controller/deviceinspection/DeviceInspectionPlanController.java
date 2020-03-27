package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionPlan;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionPlanRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检计划")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-plan")
public class DeviceInspectionPlanController {
    @Autowired
    DeviceInspectionPlanService deviceInspectionPlanService;

    @ApiOperation(value = "新增设备巡检计划" ,notes="新增设备巡检计划")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionPlan request){
        return deviceInspectionPlanService.add(request);
    }

    @ApiOperation(value = "分页查询设备巡检计划", notes = "分页查询设备巡检计划")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody DeviceInspectionPlanRequest request){
        return deviceInspectionPlanService.findByPage(request);
    }

    @ApiOperation(value = "条件查询巡检计划", notes = "条件查询巡检计划")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody DeviceInspectionPlanRequest request){
        return deviceInspectionPlanService.findBy(request);
    }

    @ApiOperation(value = "删除保洁巡检计划", notes = "删除保洁巡检计划")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionPlan request){
        return deviceInspectionPlanService.delete(request.getId());
    }

    @ApiOperation(value = "启用保洁巡检计划", notes = "启用保洁巡检计划")
    @PostMapping(value = "enable")
    public Object enable(@RequestBody @Valid DeviceInspectionPlan request){
        return deviceInspectionPlanService.enable(request.getId());
    }


    @ApiOperation(value = "禁用保洁巡检计划", notes = "禁用保洁巡检计划")
    @PostMapping(value = "disable")
    public Object disable(@RequestBody @Valid DeviceInspectionPlan request){
        return deviceInspectionPlanService.disable(request.getId());
    }

    @ApiOperation(value = "更新设备巡检计划" ,notes="更新设备巡检计划")
    @PostMapping(value = "/update")
    public Object update(@RequestBody @Valid DeviceInspectionPlan request){
        return deviceInspectionPlanService.update(request);
    }


}
