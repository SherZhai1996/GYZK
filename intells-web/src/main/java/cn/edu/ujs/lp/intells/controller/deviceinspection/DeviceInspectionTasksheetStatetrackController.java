package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheetStatetrack;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTasksheetStatetrackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "设备巡检工单状态表")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-tasksheet-statetrack")
public class DeviceInspectionTasksheetStatetrackController {
    @Autowired
    DeviceInspectionTasksheetStatetrackService deviceInspectionTasksheetStatetrackService;

    @ApiOperation(value = "新增巡检工单状态表", notes = "新增巡检工单状态表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionTasksheetStatetrack request){
        return deviceInspectionTasksheetStatetrackService.add(request);
    }


}
