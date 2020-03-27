package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionItemContent;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionItemContentRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTemplateRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionItemContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "保洁巡检内容")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-itemcontent")
public class CleaningInspectionItemContentController {
    @Autowired
    CleaningInspectionItemContentService cleaningInspectionItemContentService;

    @ApiOperation(value = "新增保洁巡检内容" ,notes="新增保洁巡检内容")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionItemContent request){
        return cleaningInspectionItemContentService.add(request);
    }

    @ApiOperation(value = "查找保洁巡检内容" ,notes="查找保洁巡检内容")
    @PostMapping(value = "/find")
    public Map<String, Object> tableList(){
//        List<CleaningInspectionItemContent> tableList = cleaningInspectionItemContentService.listTable();
        Map tableList = cleaningInspectionItemContentService.listTable();
//        Map<String,Object> tableData = new HashMap<>();
//        tableData.put("tableList",tableList);
        return tableList;
    }

    @ApiOperation(value = "删除保洁巡检内容", notes = "删除保洁巡检内容")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionItemContent request){
        return cleaningInspectionItemContentService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检内容", notes = "修改保洁巡检内容")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionItemContent request){
        return cleaningInspectionItemContentService.update(request);
    }

//    @ApiOperation(value = "分页查询保洁巡检内容", notes = "分页查询保洁巡检内容")
//    @PostMapping(value = "findByPage")
//    Map<String,Object> findByPage(@RequestBody CleaningInspectionItemContentRequest request){
//        return cleaningInspectionItemContentService.findByPage(request);
//    }

    @ApiOperation(value = "按照模板id查找项目名称项目内容",notes = "按照模板id查找项目名称项目内容")
    @PostMapping(value = "findByTemplateId")
    Map<String,Object> findByTemplateId(@RequestBody CleaningInspectionItemContent request){
        return cleaningInspectionItemContentService.findByTemplateId(request.getId());
    }

    @ApiOperation(value = "按照项目id筛选项目内容",notes = "按照项目id筛选项目内容")
    @PostMapping(value = "findByItemId")
    public Object findByItemId(@RequestBody CleaningInspectionItemContentRequest request){
        Map tableList = cleaningInspectionItemContentService.findByItemId(request);
        return tableList;
    }


}
