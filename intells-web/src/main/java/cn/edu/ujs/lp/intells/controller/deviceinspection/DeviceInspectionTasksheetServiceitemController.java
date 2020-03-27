package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheet;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheetServiceitem;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTasksheetServiceitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "设备巡检工单服务事项")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-tasksheet-serviceitem")
public class DeviceInspectionTasksheetServiceitemController {

    @Autowired
    DeviceInspectionTasksheetServiceitemService deviceInspectionTasksheetServiceitemService;

    @ApiOperation(value = "新建设备巡检工单服务事项表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionTasksheetServiceitem request){
        return deviceInspectionTasksheetServiceitemService.add(request);
    }
}
