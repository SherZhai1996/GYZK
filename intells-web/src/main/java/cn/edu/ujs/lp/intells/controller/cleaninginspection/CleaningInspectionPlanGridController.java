package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionPlanGrid;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionPlanGridRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionPlanGridService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "巡检计划网格关联")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-plan-grid")
public class CleaningInspectionPlanGridController {
    @Autowired
    CleaningInspectionPlanGridService cleaningInspectionPlanGridService;

    @ApiOperation(value = "新增巡检计划网格关联" ,notes="新增巡检计划网格关联")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionPlanGrid request){
        return cleaningInspectionPlanGridService.add(request);
    }

    @ApiOperation(value = "查找巡检计划网格关联" ,notes="查找巡检计划网格关联")
    @PostMapping(value = "/find")
    public Map<String, Object> List(){
        Map tableList = cleaningInspectionPlanGridService.list();
        return tableList;
    }

    @ApiOperation(value = "删除巡检计划网格关联", notes = "删除巡检计划网格关联")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionPlanGrid request){
        return cleaningInspectionPlanGridService.delete(request.getId());
    }

    @ApiOperation(value = "修改巡检计划网格关联", notes = "修改巡检计划网格关联")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionPlanGrid request){
        return cleaningInspectionPlanGridService.update(request);
    }

    @ApiOperation(value = "分页查询巡检计划网格关联", notes = "分页查询巡检计划网格关联")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionPlanGridRequest request){
        return cleaningInspectionPlanGridService.findByPage(request);
    }

    @ApiOperation(value = "查找网格名称" ,notes="查找网格名称")
    @PostMapping(value = "/findGridName")
    public Map<String, Object> tableList(){
        Map tableList = cleaningInspectionPlanGridService.listTable();
        return tableList;
    }

    @ApiOperation(value = "通过计划id查找网格名称",notes = "通过计划id查找网格名称")
    @PostMapping(value = "findByPlanId")
    Map<String,Object> findByPlanId(@RequestBody CleaningInspectionPlanGridRequest request){
        return cleaningInspectionPlanGridService.findByPlanId(request);
    }


}
