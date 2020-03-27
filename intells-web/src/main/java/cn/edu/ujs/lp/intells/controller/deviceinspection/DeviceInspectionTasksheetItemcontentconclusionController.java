package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheetItemcontentconclusion;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTasksheetItemconclusionRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionTasksheetItemcontentconclusionRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTasksheetItemcontentconclusionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检工单结论表")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-tasksheet-itemcontentconclusion")
public class DeviceInspectionTasksheetItemcontentconclusionController {
    @Autowired
    DeviceInspectionTasksheetItemcontentconclusionService deviceInspectionTasksheetItemcontentconclusionService;

    @ApiOperation(value = "新增保洁巡检工单巡检项结论表" ,notes="新增保洁巡检工单巡检项结论表")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionTasksheetItemcontentconclusion request){
        return deviceInspectionTasksheetItemcontentconclusionService.add(request);
    }

    @ApiOperation(value = "修改保洁巡检工单巡检项结论表", notes = "修改保洁巡检工单巡检项结论表")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid DeviceInspectionTasksheetItemcontentconclusion request){
        return deviceInspectionTasksheetItemcontentconclusionService.update(request);
    }

    @ApiOperation(value = "查找结论",notes = "查找结论")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody @Valid DeviceInspectionTasksheetItemcontentconclusionRequest request){
        return deviceInspectionTasksheetItemcontentconclusionService.findBy(request);
    }
}
