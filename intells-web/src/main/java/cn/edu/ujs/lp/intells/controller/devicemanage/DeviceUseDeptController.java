package cn.edu.ujs.lp.intells.controller.devicemanage;

import cn.edu.ujs.lp.intells.operationmaintenance.entity.devicemanage.DeviceUseDept;
import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DeviceUseDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "使用科室关联表")
@CrossOrigin
@RestController
@RequestMapping(value = "/device-use-dept")
public class DeviceUseDeptController {
    @Autowired
    DeviceUseDeptService deviceUseDeptService;

    @ApiOperation(value = "新增设备使用科室关联表",notes = "新增设备使用科室关联表")
    @PostMapping(value = "add")
    public Object add(@RequestBody DeviceUseDept request){
        return deviceUseDeptService.add(request);
    }

    @ApiOperation(value = "修改设备使用科室",notes = "修改设备使用科室")
    @PostMapping(value = "update")
    public Object update(@RequestBody DeviceUseDept request){
        return  deviceUseDeptService.update(request);
    }
}
