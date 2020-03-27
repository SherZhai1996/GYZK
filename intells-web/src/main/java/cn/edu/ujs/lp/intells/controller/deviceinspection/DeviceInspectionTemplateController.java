package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTemplate;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTemplate1;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionTemplateRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectiontemplateSaveRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检模板")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-template")
public class DeviceInspectionTemplateController {

    @Autowired
    DeviceInspectionTemplateService deviceInspectionTemplateService;
    @Autowired
    HttpServletRequest header;

    @ApiOperation(value = "新增设备巡检模板", notes = "新增设备巡检模板")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionTemplate request){
        return deviceInspectionTemplateService.add(request);
    }

    @ApiOperation(value = "更新设备巡检模板",notes = "更新设备巡检模板")
    @PostMapping(value = "/update")
    public Object update(@RequestBody @Valid DeviceInspectionTemplate request){
        return deviceInspectionTemplateService.update(request);
    }


    @ApiOperation(value = "删除设备巡检模板", notes = "删除设备巡检模板")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionTemplate request){
        return deviceInspectionTemplateService.delete(request.getId());
    }

    @ApiOperation(value = "分页查询设备巡检模板", notes = "分页查询设备巡检模板")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody DeviceInspectionTemplateRequest request){
        return deviceInspectionTemplateService.findByPage(request);
    }

    @ApiOperation(value = "按照模板名称和设备名称查找",notes = "按照模板名称和设备名称查找")
    @PostMapping(value = "findByTemplate")
    public Object findByTemplate(@RequestBody DeviceInspectionTemplateRequest request){
        return deviceInspectionTemplateService.findByTemplate(request);
    }

    @ApiOperation(value = "查询全部设备巡检模板",notes = "查询全部设备巡检模板")
    @PostMapping(value = "findAll")
    Map<String,Object> findAll(){
        return deviceInspectionTemplateService.findAll();
    }


    @ApiOperation(value = "保存一个保安巡检模板信息，可以是新增或更新",notes = "保存一个保安巡检模板信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<DeviceInspectionTemplate1> save(@RequestBody @Valid DeviceInspectiontemplateSaveRequest request) {
        DeviceInspectionTemplate1 st = null;

        try {
            if (header != null)
                st = deviceInspectionTemplateService.save(header.getHeader("hosp-ID"), request);
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


    @ApiOperation(value = "获取指定ID的设备巡检模板完整信息",notes = "获取指定ID的设备巡检模板完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (deviceInspectionTemplateService != null)
        {
            try {
                DeviceInspectionTemplate1 st = deviceInspectionTemplateService.getbyId(id);
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
