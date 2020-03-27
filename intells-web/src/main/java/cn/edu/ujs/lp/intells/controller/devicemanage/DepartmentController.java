package cn.edu.ujs.lp.intells.controller.devicemanage;

import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "部门信息")
@CrossOrigin
@RestController
@RequestMapping(value = "/department1")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "查找科室", notes = "查找科室")
    @PostMapping(value = "findAll")
    public Object findAll(){
        return departmentService.findAll();
    }
}
