package cn.edu.ujs.lp.intells.controller.devicemanage;

import cn.edu.ujs.lp.intells.operationmaintenance.request.devicemanage.HosStaffRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.HosStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "管理员信息")
@CrossOrigin
@RestController
@RequestMapping(value = "/staff1")
public class HosStaffController {
    @Autowired
    HosStaffService hosStaffService;

    @ApiOperation(value = "查询管理员信息",notes = "查询管理员信息")
    @PostMapping(value = "/findAll")
    public Object findAll(){
        return hosStaffService.findAll();
    }

    @ApiOperation(value = "按照所属科室查找",notes = "按照所属科室查找")
    @PostMapping(value = "findByDept")
    public Object findByDept(@RequestBody HosStaffRequest request){
        return hosStaffService.findByDept(request);
    }

//    @ApiOperation(value = "更新管理")
}
