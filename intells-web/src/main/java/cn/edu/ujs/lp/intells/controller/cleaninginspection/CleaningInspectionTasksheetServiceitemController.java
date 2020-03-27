package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheetServiceitem;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTasksheetServiceitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "保洁巡检工单服务事项")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-tasksheet-serviceitem")
public class CleaningInspectionTasksheetServiceitemController {

    @Autowired
    CleaningInspectionTasksheetServiceitemService cleaningInspectionTasksheetServiceitemService;

    @ApiOperation(value = "新建保洁巡检工单服务事项表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionTasksheetServiceitem request){
        return cleaningInspectionTasksheetServiceitemService.add(request);
    }
}
