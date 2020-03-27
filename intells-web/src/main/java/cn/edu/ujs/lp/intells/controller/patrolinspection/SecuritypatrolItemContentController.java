package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolItemContent;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolItemContentRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolItemContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检内容")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-itemcontent")
public class SecuritypatrolItemContentController {
    @Autowired
    SecuritypatrolItemContentService securitypatrolItemContentService;

    @ApiOperation(value = "新增保安巡检内容" ,notes="新增保安巡检内容")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolItemContent request){
        return securitypatrolItemContentService.add(request);
    }

    @ApiOperation(value = "查找保安巡检内容" ,notes="查找保安巡检内容")
    @PostMapping(value = "/find")
    public Map<String, Object> tableList(){
        Map tableList = securitypatrolItemContentService.listTable();
        return tableList;
    }

    @ApiOperation(value = "删除保安巡检内容", notes = "删除保安巡检内容")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolItemContent request){
        return securitypatrolItemContentService.delete(request.getId());
    }

    @ApiOperation(value = "修改保安巡检内容", notes = "修改保安巡检内容")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolItemContent request){
        return securitypatrolItemContentService.update(request);
    }


    @ApiOperation(value = "按照模板id查找项目名称项目内容",notes = "按照模板id查找项目名称项目内容")
    @PostMapping(value = "findByTemplateId")
    Map<String,Object> findByTemplateId(@RequestBody SecuritypatrolItemContent request){
        return securitypatrolItemContentService.findByTemplateId(request.getId());
    }

    @ApiOperation(value = "按照项目id筛选项目内容",notes = "按照项目id筛选项目内容")
    @PostMapping(value = "findByItemId")
    public Object findByItemId(@RequestBody SecuritypatrolItemContentRequest request){
        Map tableList = securitypatrolItemContentService.findByItemId(request);
        return tableList;
    }
}
