package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheetUndertake;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheet;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheetUndertake;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolTasksheetUndertakeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "保安巡检工单派工信息")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-tasksheet-undertake")
public class SecuritypatrolTasksheetUndertakeController {
    @Autowired
    SecuritypatrolTasksheetUndertakeService securitypatrolTasksheetUndertakeService;

    @ApiOperation(value = "新建保安巡检派工信息",notes = "新建保安巡检派工信息")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolTasksheetUndertake request){
        return securitypatrolTasksheetUndertakeService.add(request);
    }
}
