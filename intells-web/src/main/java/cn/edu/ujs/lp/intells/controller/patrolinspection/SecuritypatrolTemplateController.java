package cn.edu.ujs.lp.intells.controller.patrolinspection;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTemplate;
import cn.edu.ujs.lp.intells.patrolinspection.entity.SecuritypatrolTemplate1;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatrolTemplateRequest;
import cn.edu.ujs.lp.intells.patrolinspection.request.SecuritypatroltemplateSaveRequest;
import cn.edu.ujs.lp.intells.patrolinspection.service.SecuritypatrolTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "保安巡检模板")
@CrossOrigin
@RestController
@RequestMapping(value = "/securitypatrol-template")
public class SecuritypatrolTemplateController {
    @Autowired
    SecuritypatrolTemplateService securitypatrolTemplateService;
    @Autowired
    HttpServletRequest header;

    @ApiOperation(value = "新增保安巡检模板" ,notes="新增保安巡检模板")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid SecuritypatrolTemplate request){
        return securitypatrolTemplateService.add(request);
    }

    @ApiOperation(value = "删除保洁巡检模板", notes = "删除保洁巡检模板")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid SecuritypatrolTemplate request){
        return securitypatrolTemplateService.delete(request.getId());
    }

    @ApiOperation(value = "修改保洁巡检模板", notes = "修改保洁巡检模板")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid SecuritypatrolTemplate request){
        return securitypatrolTemplateService.update(request);
    }

    @ApiOperation(value = "按照网格场所查找巡检模板", notes = "按照网格场所查找巡检模板")
    @PostMapping(value = "findByGridPlaceclassId")
    public Object findByInspectiontype(@RequestBody SecuritypatrolTemplateRequest request){
        Map tableList = securitypatrolTemplateService.templateList(request);
        return tableList;
    }

    @ApiOperation(value = "条件查找巡检模板", notes = "条件查找巡检模板")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody SecuritypatrolTemplateRequest request){
        return securitypatrolTemplateService.findBy(request);
    }

    @ApiOperation(value = "分页查询保洁巡检模板", notes = "分页查询保洁巡检模板")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody SecuritypatrolTemplateRequest request){
        return securitypatrolTemplateService.findByPage(request);
    }


    @ApiOperation(value = "下载保安巡检模板Excel模板",notes = "下载保安巡检模板Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return securitypatrolTemplateService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"保安巡检模板Excel模板下载失败"+e.getMessage());
        }
    }


    @ApiOperation(value = "上传保安巡检模板Excel文件",notes = "上传保安巡检模板Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return securitypatrolTemplateService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，保安巡检模板Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "保安巡检模板Excel文件失败:" + e.getMessage());
        }
    }



    @ApiOperation(value = "导出保安巡检模板Excel数据",notes = "导出保安巡检模板Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return securitypatrolTemplateService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出保安巡检模板Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出保安巡检模板Excel数据失败:" + e.getMessage());
        }
    }



    @ApiOperation(value = "保存一个保安巡检模板信息，可以是新增或更新",notes = "保存一个保安巡检模板信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<SecuritypatrolTemplate1> save(@RequestBody @Valid SecuritypatroltemplateSaveRequest request) {
        SecuritypatrolTemplate1 st = null;

        try {
            if (header != null)
                st = securitypatrolTemplateService.save(header.getHeader("hosp-ID"), request);
            else
                return JsonResponse.fail(1001, "没有指定医院，保存保安巡检模板失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "保存保安巡检模板失败：" + e.getMessage());
        }

        if (st == null)
            return JsonResponse.fail(1001, "保存保安巡检模板信息失败");
        else
            return JsonResponse.success("保存保安巡检模板信息成功", st);
    }


    @ApiOperation(value = "获取指定ID的保安巡检模板完整信息",notes = "获取指定ID的保安巡检模板完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (securitypatrolTemplateService != null)
        {
            try {
                SecuritypatrolTemplate1 st = securitypatrolTemplateService.getbyId(id);
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


    @ApiOperation(value = "根据查找ispatrol",notes = "根据查找ispatrol")
    @PostMapping(value = "findIspatrolById")
    Map<String,Object> findIspatrolById(@RequestBody SecuritypatrolTemplate request){
        return securitypatrolTemplateService.findIspatrolById(request);
    }


}
