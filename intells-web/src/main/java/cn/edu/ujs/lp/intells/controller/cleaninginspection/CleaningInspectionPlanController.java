package cn.edu.ujs.lp.intells.controller.cleaninginspection;

import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionPlan;
import cn.edu.ujs.lp.intells.operationmaintenance.dao.cleaninginspection.CleaningInspectionPlanRepository;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionPlanRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检计划")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-plan")
public class CleaningInspectionPlanController {
    @Autowired
    CleaningInspectionPlanService cleaningInspectionPlanService;

    @Autowired
    CleaningInspectionPlanRepository cleaningInspectionPlanRepository;

    @ApiOperation(value = "新增保洁巡检计划" ,notes="新增保洁巡检计划")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionPlan request){
        return cleaningInspectionPlanService.add(request);
    }



    @ApiOperation(value = "删除保洁巡检计划", notes = "删除保洁巡检计划")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionPlan request){
        return cleaningInspectionPlanService.delete(request.getId());
    }

    @ApiOperation(value = "停用保洁巡检计划", notes = "停用保洁巡检计划")
    @PostMapping(value = "disable")
    public Object disable(@RequestBody @Valid CleaningInspectionPlan request){
        return cleaningInspectionPlanService.disable(request.getId());
    }

    @ApiOperation(value = "启用保洁巡检计划", notes = "启用保洁巡检计划")
    @PostMapping(value = "enable")
    public Object enable(@RequestBody @Valid CleaningInspectionPlan request){
        return cleaningInspectionPlanService.enable(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检计划", notes = "修改保洁巡检计划")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionPlan request){
        return cleaningInspectionPlanService.update(request);
    }

    @ApiOperation(value = "分页查询保洁巡检计划", notes = "分页查询保洁巡检计划")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionPlanRequest request){
        return cleaningInspectionPlanService.findByPage(request);
    }

    @ApiOperation(value = "条件查询巡检计划", notes = "条件查询巡检计划")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody CleaningInspectionPlanRequest request){
        return cleaningInspectionPlanService.findBy(request);
    }


}
