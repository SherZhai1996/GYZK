package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolPlanGrid;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolPlanGridRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolPlanGridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "巡检计划网格关联")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-plan-grid")
public class SecuritypatrolPlanGridController {

    @Autowired
    SecuritypatrolPlanGridService securitypatrolPlanGridService;

    @ApiOperation(value = "新增巡检计划网格关联" ,notes="新增巡检计划网格关联")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolPlanGrid request){
        return securitypatrolPlanGridService.add(request);
    }


    @ApiOperation(value = "查找巡检计划网格关联" ,notes="查找巡检计划网格关联")
    @PostMapping(value = "/find")
    public Map<String, Object> List(){
        Map tableList = securitypatrolPlanGridService.list();
        return tableList;
    }

    @ApiOperation(value = "删除巡检计划网格关联", notes = "删除巡检计划网格关联")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolPlanGrid request){
        return securitypatrolPlanGridService.delete(request.getId());
    }

    @ApiOperation(value = "修改巡检计划网格关联", notes = "修改巡检计划网格关联")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolPlanGrid request){
        return securitypatrolPlanGridService.update(request);
    }

    @ApiOperation(value = "分页查询巡检计划网格关联", notes = "分页查询巡检计划网格关联")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolPlanGridRequest request){
        return securitypatrolPlanGridService.findByPage(request);
    }

    @ApiOperation(value = "查找网格名称" ,notes="查找网格名称")
    @PostMapping(value = "/findGridName")
    public Map<String, Object> tableList(){
        Map tableList = securitypatrolPlanGridService.listTable();
        return tableList;
    }

    @ApiOperation(value = "通过计划id查找网格名称",notes = "通过计划id查找网格名称")
    @PostMapping(value = "findByPlanId")
    Map<String,Object> findByPlanId(@RequestBody SecuritypatrolPlanGridRequest request){
        return securitypatrolPlanGridService.findByPlanId(request);
    }
}
