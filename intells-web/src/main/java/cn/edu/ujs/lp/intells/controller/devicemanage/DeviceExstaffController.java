package cn.edu.ujs.lp.intells.controller.devicemanage;

import cn.edu.ujs.lp.intells.operationmaintenance.entity.devicemanage.DeviceExstaff;
import cn.edu.ujs.lp.intells.operationmaintenance.request.devicemanage.DeviceExstaffRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DeviceExstaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "外委员工")
@CrossOrigin
@RestController
@RequestMapping(value = "/exstaff")
public class DeviceExstaffController {

    @Autowired
    DeviceExstaffService deviceExstaffService;

    @ApiOperation(value = "查询外委员工",notes = "查询外委员工")
    @PostMapping(value = "/findAll")
    public Object findAll(){
        return deviceExstaffService.findAll();
    }


    @ApiOperation(value = "条件查询",notes = "条件查询")
    @PostMapping(value = "/findBy")
    public Object findBy(@RequestBody DeviceExstaffRequest request){
        return deviceExstaffService.findBy(request);
    }
}
