package cn.edu.ujs.lp.intells.controller.cleaninginspection;

import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionItem;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionItemRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "保洁巡检项目")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-item")
public class CleaningInspectionItemController {
    @Autowired
    CleaningInspectionItemService cleaningInspectionItemService;


    @ApiOperation(value = "新增保洁巡检项目" ,notes="新增保洁巡检项目")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionItem request){
        return cleaningInspectionItemService.add(request);
    }


    @ApiOperation(value = "删除保洁巡检项目", notes = "删除保洁巡检项目")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionItem request){
        return cleaningInspectionItemService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检项目", notes = "修改保洁巡检项目")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionItem request){
        return cleaningInspectionItemService.update(request);
    }

    @ApiOperation(value = "分页查询保洁巡检项目", notes = "分页查询保洁巡检项目")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionItemRequest request){
        return cleaningInspectionItemService.findByPage(request);
    }



    @ApiOperation(value = "根据模板id查询", notes = "条根据模板id查询")
    @PostMapping(value = "findByTemplateId")
    public Object findByTemplateId(@RequestBody CleaningInspectionItemRequest request){
        Map tableList = cleaningInspectionItemService.templateList(request);
        return tableList;
    }

}
