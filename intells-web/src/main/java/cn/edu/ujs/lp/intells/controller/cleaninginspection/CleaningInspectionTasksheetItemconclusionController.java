package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheet;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTasksheetItemconclusionRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTasksheetItemconclusionService;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolTasksheetItemconclusionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检结论")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-tasksheet-itemconclusion")
public class CleaningInspectionTasksheetItemconclusionController {
    @Autowired
    CleaningInspectionTasksheetItemconclusionService cleaningInspectionTasksheetItemconclusionService;

    @ApiOperation(value = "条件查找巡检结论", notes = "条件查找巡检结论")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody @Valid CleaningInspectionTasksheetItemconclusionRequest request){
        return cleaningInspectionTasksheetItemconclusionService.findBy(request);
    }
}
