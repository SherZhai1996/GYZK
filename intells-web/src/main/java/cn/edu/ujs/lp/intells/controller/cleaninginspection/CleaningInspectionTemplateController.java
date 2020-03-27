package cn.edu.ujs.lp.intells.controller.cleaninginspection;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTemplate;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTemplate1;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTemplateRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectiontemplateSaveRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检模板")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-template")
public class CleaningInspectionTemplateController {
    @Autowired
    CleaningInspectionTemplateService cleaningInspectionTemplateService;
    @Autowired
    HttpServletRequest header;

    @ApiOperation(value = "新增保洁巡检模板" ,notes="新增保洁巡检模板")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid CleaningInspectionTemplate request){
        return cleaningInspectionTemplateService.add(request);
    }


    @ApiOperation(value = "删除保洁巡检模板", notes = "删除保洁巡检模板")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionTemplate request){
        return cleaningInspectionTemplateService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检模板", notes = "修改保洁巡检模板")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionTemplate request){
        return cleaningInspectionTemplateService.update(request);
    }

    @ApiOperation(value = "按照网格场所查找巡检模板", notes = "按照网格场所查找巡检模板")
    @PostMapping(value = "findByGridPlaceclassId")
    public Object findByInspectiontype(@RequestBody CleaningInspectionTemplateRequest request){
        Map tableList = cleaningInspectionTemplateService.templateList(request);
        return tableList;
    }

    @ApiOperation(value = "条件查找巡检模板", notes = "条件查找巡检模板")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody CleaningInspectionTemplateRequest request){
        return cleaningInspectionTemplateService.findBy(request);
    }

    @ApiOperation(value = "分页查询保洁巡检模板", notes = "分页查询保洁巡检模板")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionTemplateRequest request){
        return cleaningInspectionTemplateService.findByPage(request);
    }


    @ApiOperation(value = "保存一个保洁巡检模板信息，可以是新增或更新",notes = "保存一个保洁巡检模板信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<CleaningInspectionTemplate1> save(@RequestBody @Valid CleaningInspectiontemplateSaveRequest request) {
        CleaningInspectionTemplate1 st = null;

        try {
            if (header != null)
                st = cleaningInspectionTemplateService.save(header.getHeader("hosp-ID"), request);
            else
                return JsonResponse.fail(1001, "没有指定医院，保存保洁巡检模板失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "保存保洁巡检模板失败：" + e.getMessage());
        }

        if (st == null)
            return JsonResponse.fail(1001, "保存保洁巡检模板信息失败");
        else
            return JsonResponse.success("保存保洁巡检模板信息成功", st);
    }


    @ApiOperation(value = "获取指定ID的保安巡检模板完整信息",notes = "获取指定ID的保安巡检模板完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (cleaningInspectionTemplateService != null)
        {
                try {
                CleaningInspectionTemplate1 st = cleaningInspectionTemplateService.getbyId(id);
                return JsonResponse.success("获取指定ID的保安巡检模板完整信息成功",st);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的保安巡检模板完整信息失败");
    }

}
