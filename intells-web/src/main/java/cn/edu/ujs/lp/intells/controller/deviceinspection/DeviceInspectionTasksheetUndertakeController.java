package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheet;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheetUndertake;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTasksheetUndertakeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "设备巡检工单派工信息表")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-tasksheet-undertake")
public class DeviceInspectionTasksheetUndertakeController {

    @Autowired
    DeviceInspectionTasksheetUndertakeService deviceInspectionTasksheetUndertakeService;

    @ApiOperation(value = "新增设备巡检工单派工信息表", notes = "新增设备巡检工单派工信息表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionTasksheetUndertake request){
        return deviceInspectionTasksheetUndertakeService.add(request);
    }
}
