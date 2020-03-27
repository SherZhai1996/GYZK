package cn.edu.ujs.lp.intells.controller.cleaninginspection;

import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheetUndertake;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTasksheetUndertakeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "保洁巡检工单派工信息")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaaninginspection-tasksheet-undertake")
public class CleaningInspectionTasksheetUndertakeController {
    @Autowired
    CleaningInspectionTasksheetUndertakeService cleaningInspectionTasksheetUndertakeService;

    @ApiOperation(value = "新建保洁巡检派工信息",notes = "新建保洁巡检派工信息")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionTasksheetUndertake request){
        return cleaningInspectionTasksheetUndertakeService.add(request);
    }
}
