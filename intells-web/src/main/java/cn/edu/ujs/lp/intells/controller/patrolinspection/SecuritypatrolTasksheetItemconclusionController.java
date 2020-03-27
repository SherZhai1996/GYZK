package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolTasksheetItemconclusionRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecurtiypatrolTasksheetItemconclusionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检结论")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-tasksheet-itemconclusion")
public class SecuritypatrolTasksheetItemconclusionController {
    @Autowired
    SecurtiypatrolTasksheetItemconclusionService securitypatrolTasksheetItemconclusionService;

    @ApiOperation(value = "条件查找巡检结论", notes = "条件查找巡检结论")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody @Valid SecuritypatrolTasksheetItemconclusionRequest request){
        return securitypatrolTasksheetItemconclusionService.findBy(request);
    }
}
