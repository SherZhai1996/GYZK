package cn.edu.ujs.lp.intells.controller.devicemanage;

import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DeviceExteamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "外委班组")
@CrossOrigin
@RestController
@RequestMapping(value = "/exteam")
public class DeviceExteamController {
    @Autowired
    DeviceExteamService deviceExteamService;

    @ApiOperation(value = "查询外委班组",notes = "查询外委班组")
    @PostMapping(value = "/findAll")
    public Object findAll(){
        return deviceExteamService.findAll();
    }
}
