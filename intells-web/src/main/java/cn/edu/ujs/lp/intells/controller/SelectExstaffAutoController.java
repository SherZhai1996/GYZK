package cn.edu.ujs.lp.intells.controller;

import cn.edu.ujs.lp.intells.servicecenter.request.SelectExstaffAutoRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.TasksheetUndertakeRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.SelectPersonAutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description = "选人与拒单接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/SelectExstaffAutoController")
public class SelectExstaffAutoController {
    @Autowired
    SelectPersonAutoService selectPersonAuto;
    @PostMapping(value = "/select")
    @ApiOperation(value = "选人" ,  notes="选人")
    public Map<String, Object> selectExstaffAutoController(@RequestBody SelectExstaffAutoRequest request){
        return selectPersonAuto.selectExstaffAuto(request);
    }
    @PostMapping(value = "/refuseReceiveTasksheet")
    @ApiOperation(value = "拒单接口" ,  notes="拒单接口")
    public Map<String, Object> refuseReceiveTasksheet(@RequestBody TasksheetUndertakeRequest request){
        return selectPersonAuto.refuseReceiveTasksheet(request);
    }
}
