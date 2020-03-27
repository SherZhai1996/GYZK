package cn.edu.ujs.lp.intells.controller.deviceinspection;



import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionTasksheet;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionTasksheetRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionTasksheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检工单")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-tasksheet")
public class DeviceInspectionTasksheetController {
    @Autowired
    DeviceInspectionTasksheetService deviceInspectionTasksheetService;

    @ApiOperation(value = "新增周巡检工单" ,notes="新增周巡检工单")
    @PostMapping(value = "/addWeekTasksheet")
    public Object addWeekTasksheet(@RequestBody @Valid DeviceInspectionTasksheetRequest request) throws Exception{
        return deviceInspectionTasksheetService.addWeekTasksheet(request);
    }

    @ApiOperation(value = "新增月巡检工单" ,notes="新增月巡检工单")
    @PostMapping(value = "/addMonthTasksheet")
    public Object addMonthTasksheet(@RequestBody @Valid DeviceInspectionTasksheetRequest request) throws Exception{
        return deviceInspectionTasksheetService.addMonthTasksheet(request);
    }

    @ApiOperation(value = "新增年巡检工单" ,notes="新增年巡检工单")
    @PostMapping(value = "/addYearTasksheet")
    public Object addYearTasksheet(@RequestBody @Valid DeviceInspectionTasksheetRequest request) throws Exception{
        return deviceInspectionTasksheetService.addYearTasksheet(request);
    }

    @ApiOperation(value = "新增季度巡检工单" ,notes="新增季度巡检工单")
    @PostMapping(value = "/addQuarterTasksheet")
    public Object addQuarterTasksheet(@RequestBody @Valid DeviceInspectionTasksheetRequest request) throws Exception{
        return deviceInspectionTasksheetService.addQuarterTasksheet(request);
    }

    @ApiOperation(value = "新增天巡检工单" ,notes="新增天巡检工单")
    @PostMapping(value = "/addDayTasksheet")
    public Object addDayTasksheet(@RequestBody @Valid DeviceInspectionTasksheetRequest request) throws Exception{
        return deviceInspectionTasksheetService.addDayTasksheet(request);
    }


    @ApiOperation(value = "修改巡检工单", notes = "修改巡检工单")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid DeviceInspectionTasksheetRequest request){
        return deviceInspectionTasksheetService.update(request);
    }

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @ResponseBody
    @PostMapping(value = "uploadPic")
    public JsonResponse uploadPic (HttpServletRequest request){
        return deviceInspectionTasksheetService.uploadPic(request);
    }

    @ApiOperation(value = "删除图片",notes = "删除图片")
    @ResponseBody
    @PostMapping(value = "clearUploadPic")
    public JsonResponse clearUploadPic (@RequestBody @Valid String id){
        return deviceInspectionTasksheetService.clearUploadPic(id);
    }

    @ApiOperation(value = "上传音频",notes = "上传音频")
    @ResponseBody
    @PostMapping(value = "uploadWav")
    public JsonResponse uploadWav (HttpServletRequest request){
        return deviceInspectionTasksheetService.uploadWav(request);
    }

    @ApiOperation(value = "删除音频",notes = "删除音频")
    @ResponseBody
    @PostMapping(value = "clearUploadWav")
    public JsonResponse clearUploadWav (@RequestBody @Valid String id){
        return deviceInspectionTasksheetService.clearUploadWav(id);
    }


    @ApiOperation(value = "分页查询巡检工单", notes = "分页查询巡检工单")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody DeviceInspectionTasksheetRequest request){
        return deviceInspectionTasksheetService.findByPage(request);
    }

    @ApiOperation(value = "条件查询巡检工单", notes = "条件查询巡检工单")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody DeviceInspectionTasksheetRequest request){
        return deviceInspectionTasksheetService.findBy(request);
    }

}
