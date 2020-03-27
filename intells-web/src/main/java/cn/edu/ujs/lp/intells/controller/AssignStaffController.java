package cn.edu.ujs.lp.intells.controller;

import cn.edu.ujs.lp.intells.phoneservice.entity.AssignStaff;
import cn.edu.ujs.lp.intells.phoneservice.service.AssignStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "查询班组接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/work_information")
public class AssignStaffController {

    @Autowired
    AssignStaffService assignStaffService;

    @PostMapping(value = "/getStaffPhoneNumberOnWorking")
    @ApiOperation(value = "返回派工电话" ,  notes="增加记录")
    public String getStaffPhoneNumberOnWorking(@RequestBody AssignStaff request){
        return assignStaffService.getStaffPhoneNumberOnWorking(request.getId());
    }
}
