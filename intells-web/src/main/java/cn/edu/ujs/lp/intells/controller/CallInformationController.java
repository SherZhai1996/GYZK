package cn.edu.ujs.lp.intells.controller;


import cn.edu.ujs.lp.intells.phoneservice.entity.CallInformation;
import cn.edu.ujs.lp.intells.phoneservice.service.CallInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "通话记录接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/call_information")
public class CallInformationController {
    @Autowired
    CallInformationService callInformationService;

    @PostMapping(value = "/addRecord")
    @ApiOperation(value = "增加记录" ,  notes="增加记录")
    public String addRecord(@RequestBody CallInformation record){

        return callInformationService.addRecord(record);


    }

}
