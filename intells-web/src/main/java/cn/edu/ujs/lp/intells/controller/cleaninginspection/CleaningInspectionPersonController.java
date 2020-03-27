package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionPerson;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionPersonRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检人员表")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-person")
public class CleaningInspectionPersonController {
    @Autowired
    CleaningInspectionPersonService cleaningInspectionPersonService;

    @ApiOperation(value = "新增保洁巡检人员" ,notes="新增保洁巡检人员")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionPerson request){
        return cleaningInspectionPersonService.add(request);
    }


    @ApiOperation(value = "删除保洁巡检人员", notes = "删除保洁巡检人员")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionPerson request){
        return cleaningInspectionPersonService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检人员", notes = "修改保洁巡检人员")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionPerson request){
        return cleaningInspectionPersonService.update(request);
    }

    @ApiOperation(value = "分页查询保洁巡检人员", notes = "分页查询保洁巡检人员")
    @PostMapping(value = "findByIsleader")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionPersonRequest request){
        return cleaningInspectionPersonService.findByIsleader(request);
    }


    @ApiOperation(value = "查找保洁巡检人员" ,notes="查找保洁巡检人员")
    @PostMapping(value = "/findPerson")
    public Map<String, Object> tableList(){
        Map tableList = cleaningInspectionPersonService.listTable();
        return tableList;
    }


    @ApiOperation(value = "更改保洁巡检人员状态",notes = "更改保洁巡检人员状态")
    @PostMapping(value = "updateStatus")
    public Object updateStatus (@RequestBody @Valid CleaningInspectionPerson request){
        return cleaningInspectionPersonService.updateStatus(request.getId(),request.getIsactive());
    }



}
