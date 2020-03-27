package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.dao.SecuritypatrolplanRepository;
import cn.edu.ujs.lp.intells.patrolinspection.entity.Securitypatrolplan;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolplanRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检计划")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-plan")
public class SecuritypatrolplanController {

    @Autowired
    SecuritypatrolplanService securitypatrolplanService;

    @Autowired
    SecuritypatrolplanRepository securitypatrolplanRepository;

    @ApiOperation(value = "新增保安巡检计划" ,notes="新增保安巡检计划")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid Securitypatrolplan request){
        return securitypatrolplanService.add(request);
    }



    @ApiOperation(value = "删除保洁巡检计划", notes = "删除保洁巡检计划")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid Securitypatrolplan request){
        return securitypatrolplanService.delete(request.getId());
    }

    @ApiOperation(value = "停用保洁巡检计划", notes = "停用保洁巡检计划")
    @PostMapping(value = "disable")
    public Object disable(@RequestBody @Valid Securitypatrolplan request){
        return securitypatrolplanService.disable(request.getId());
    }

    @ApiOperation(value = "启用保洁巡检计划", notes = "启用保洁巡检计划")
    @PostMapping(value = "enable")
    public Object enable(@RequestBody @Valid Securitypatrolplan request){
        return securitypatrolplanService.enable(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检计划", notes = "修改保洁巡检计划")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid Securitypatrolplan request){
        return securitypatrolplanService.update(request);
    }

    @ApiOperation(value = "分页查询保洁巡检计划", notes = "分页查询保洁巡检计划")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolplanRequest request){
        return securitypatrolplanService.findByPage(request);
    }

    @ApiOperation(value = "条件查询巡检计划", notes = "条件查询巡检计划")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody SecuritypatrolplanRequest request){
        return securitypatrolplanService.findBy(request);
    }
}
