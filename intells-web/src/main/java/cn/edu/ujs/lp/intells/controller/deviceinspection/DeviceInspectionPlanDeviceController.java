package cn.edu.ujs.lp.intells.controller.deviceinspection;



import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionPlanDevice;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionPlanDeviceRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionPlanDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "巡检计划设备关联")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-plan-device")
public class DeviceInspectionPlanDeviceController {
    @Autowired
    DeviceInspectionPlanDeviceService deviceInspectionPlanDeviceService;

    @ApiOperation(value = "新增巡检计划设备关联" ,notes="新增巡检计划设备关联")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionPlanDevice request){
        return deviceInspectionPlanDeviceService.add(request);
    }

    @ApiOperation(value = "删除巡检计划网格关联", notes = "删除巡检计划网格关联")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionPlanDevice request){
        return deviceInspectionPlanDeviceService.delete(request.getId());
    }

    @ApiOperation(value = "查找设备名称" ,notes="查找设备名称")
    @PostMapping(value = "/findDeviceName")
    public Map<String, Object> tableList(){
        Map tableList = deviceInspectionPlanDeviceService.listTable();
        return tableList;
    }

    @ApiOperation(value = "全查关联",notes = "全查关联")
    @PostMapping(value = "/findAll")
    public Map<String,Object> findAll(){
        return deviceInspectionPlanDeviceService.findAll();
    }


    @ApiOperation(value = "根据计划id和设备id查询")
    @PostMapping(value = "findByPlanIdAndDeviceId")
    public Map<String,Object> findByPlanIdAndDeviceId(@RequestBody  DeviceInspectionPlanDeviceRequest request){
        return deviceInspectionPlanDeviceService.findByPlanIdAndDeviceId(request);
    }

    @ApiOperation(value = "根据计划id查询设备名称")
    @PostMapping(value = "findByPlanId")
    public Map<String,Object> findByPlanId(@RequestBody DeviceInspectionPlanDeviceRequest request){
        return deviceInspectionPlanDeviceService.findByPlanId(request);
    }
}
