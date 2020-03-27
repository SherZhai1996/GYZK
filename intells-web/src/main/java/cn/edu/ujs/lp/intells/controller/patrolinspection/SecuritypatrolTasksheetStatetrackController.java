package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheet;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTasksheetStatetrack;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolTasksheetStatetrackRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolTasksheetStatetrackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检工单状态表")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-tasksheet-statetrack")
public class SecuritypatrolTasksheetStatetrackController {

    @Autowired
    SecuritypatrolTasksheetStatetrackService securitypatrolTasksheetStatetrackService;

    @ApiOperation(value = "新增保洁巡检工单状态表" ,notes="新增保洁巡检工单状态表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolTasksheetStatetrack request){
        return securitypatrolTasksheetStatetrackService.add(request);
    }

    @ApiOperation(value = "删除保洁巡检工单状态表", notes = "删除保洁巡检工单状态表")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolTasksheetStatetrack request){
        return securitypatrolTasksheetStatetrackService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检工单状态表", notes = "修改保洁巡检工单状态表")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolTasksheetStatetrack request){
        return securitypatrolTasksheetStatetrackService.update(request);
    }


    @ApiOperation(value = "分页查询保洁巡检工单状态表", notes = "分页查询保洁巡检工单状态表")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolTasksheetStatetrackRequest request){
        return securitypatrolTasksheetStatetrackService.findByPage(request);
    }

}
