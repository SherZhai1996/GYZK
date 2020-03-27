package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheet;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheetServiceitem;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolTasksheetServiceitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "保安巡检工单服务事项")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-tasksheet-serviceitem")
public class SecuritypatrolTasksheetServiceitemController {
    @Autowired
    SecuritypatrolTasksheetServiceitemService securitypatrolTasksheetServiceitemService;


    @ApiOperation(value = "新建保安巡检工单服务事项表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolTasksheetServiceitem request){
        return securitypatrolTasksheetServiceitemService.add(request);
    }
}
