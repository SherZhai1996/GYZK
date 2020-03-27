package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheetStatetrack;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTasksheetRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTasksheetStatetrackRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTasksheetStatetrackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检工单状态表")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-tasksheet-statetrack")
public class CleaningInspectionTasksheetStatetrackController {
    @Autowired
    CleaningInspectionTasksheetStatetrackService cleaningInspectionTasksheetStatetrackService;

    @ApiOperation(value = "新增保洁巡检工单状态表" ,notes="新增保洁巡检工单状态表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionTasksheetStatetrack request){
        return cleaningInspectionTasksheetStatetrackService.add(request);
    }


    @ApiOperation(value = "删除保洁巡检工单状态表", notes = "删除保洁巡检工单状态表")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionTasksheetStatetrack request){
        return cleaningInspectionTasksheetStatetrackService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检工单状态表", notes = "修改保洁巡检工单状态表")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionTasksheetStatetrack request){
        return cleaningInspectionTasksheetStatetrackService.update(request);
    }


    @ApiOperation(value = "分页查询保洁巡检工单状态表", notes = "分页查询保洁巡检工单状态表")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionTasksheetStatetrackRequest request){
        return cleaningInspectionTasksheetStatetrackService.findByPage(request);
    }


}
