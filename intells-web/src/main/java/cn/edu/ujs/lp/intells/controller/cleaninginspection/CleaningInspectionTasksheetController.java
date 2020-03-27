package cn.edu.ujs.lp.intells.controller.cleaninginspection;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.cleaninginspection.CleaningInspectionTasksheet;
import cn.edu.ujs.lp.intells.operationmaintenance.request.cleaninginspection.CleaningInspectionTasksheetRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.cleaninginspection.CleaningInspectionTasksheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "保洁巡检工单")
@CrossOrigin
@RestController
@RequestMapping(value = "/cleaninginspection-tasksheet")
public class CleaningInspectionTasksheetController {
    @Autowired
    CleaningInspectionTasksheetService cleaningInspectionTasksheetService;

    @ApiOperation(value = "新增周巡检工单" ,notes="新增周巡检工单")
    @PostMapping(value = "/addWeekTasksheet")
    public Map<String,Object> addWeekTasksheet(@RequestBody CleaningInspectionTasksheetRequest request) throws Exception{
        return cleaningInspectionTasksheetService.addWeekTasksheet(request);
    }

    @ApiOperation(value = "新增月巡检工单", notes = "新增月巡检工单")
    @PostMapping(value = "/addMonthTasksheet")
    public Map<String,Object> addMonthTasksheet(@RequestBody CleaningInspectionTasksheetRequest request) throws Exception{
        return cleaningInspectionTasksheetService.addMonthTasksheet(request);
    }

    @ApiOperation(value = "新增季度巡检工单",notes = "新增季度巡检工单")
    @PostMapping(value = "/addQuarterTasksheet")
    public Map<String,Object> addQuarterTasksheet(@RequestBody CleaningInspectionTasksheetRequest request) throws Exception{
        return cleaningInspectionTasksheetService.addQuarterTasksheet(request);
    }


    @ApiOperation(value = "新增年巡检工单",notes = "新增年巡检工单")
    @PostMapping(value = "/addYearTasksheet")
    public Map<String,Object> addYearTasksheet(@RequestBody CleaningInspectionTasksheetRequest request) throws Exception{
        return cleaningInspectionTasksheetService.addYearTasksheet(request);
    }


    @ApiOperation(value = "新增天巡检工单",notes = "新增天巡检工单")
    @PostMapping(value = "/addDayTasksheet")
    public Map<String,Object> addDayTasksheet(@RequestBody CleaningInspectionTasksheetRequest request) throws Exception{
        return cleaningInspectionTasksheetService.addDayTasksheet(request);
    }

    @ApiOperation(value = "删除巡检工单", notes = "删除巡检工单")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid CleaningInspectionTasksheet request){
        return cleaningInspectionTasksheetService.delete(request.getId());
    }

    @ApiOperation(value = "修改巡检工单", notes = "修改巡检工单")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.update(request);
    }

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @ResponseBody
    @PostMapping(value = "uploadPic")
    public JsonResponse uploadPic (HttpServletRequest request){
        return cleaningInspectionTasksheetService.uploadPic(request);
    }

    @ApiOperation(value = "删除图片",notes = "删除图片")
    @ResponseBody
    @PostMapping(value = "clearUploadPic")
    public JsonResponse clearUploadPic (@RequestBody @Valid String id){
        return cleaningInspectionTasksheetService.clearUploadPic(id);
    }

    @ApiOperation(value = "上传音频",notes = "上传音频")
    @ResponseBody
    @PostMapping(value = "uploadWav")
    public JsonResponse uploadWav (HttpServletRequest request){
        return cleaningInspectionTasksheetService.uploadWav(request);
    }

    @ApiOperation(value = "删除音频",notes = "删除音频")
    @ResponseBody
    @PostMapping(value = "clearUploadWav")
    public JsonResponse clearUploadWav (@RequestBody @Valid String id){
        return cleaningInspectionTasksheetService.clearUploadWav(id);
    }

    @ApiOperation(value = "分页查询巡检工单", notes = "分页查询巡检工单")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.findByPage(request);
    }

    @ApiOperation(value = "条件查询巡检工单", notes = "条件查询巡检工单")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.findBy(request);
    }

    @ApiOperation(value = "按照项目名称找项目内容", notes = "按照项目名称找项目内容")
    @PostMapping(value = "findByItem")
    Map<String,Object> findByItem(@RequestBody CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.findByItem(request);
    }

    @ApiOperation(value = "完成统计工单",notes = "完成统计工单")
    @PostMapping(value = "finishStatistic")
    Map<String,Object>finishStatistic(@RequestBody @Valid CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.finishStatistic(request);
    }

    @ApiOperation(value = "合格统计工单",notes = "当天合格统计工单")
    @PostMapping(value = "qualifyStatistic")
    Map<String,Object> qualifyStatistic(@RequestBody @Valid CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.qualifyStatistic(request);
    }


    @ApiOperation(value = "不合格巡检工单", notes = "不合格巡检工单")
    @PostMapping(value = "unqualifyTasksheet")
    Map<String,Object> unqualifyTasksheet(@RequestBody CleaningInspectionTasksheetRequest request){
        return cleaningInspectionTasksheetService.unqualifyTasksheet(request);
    }

    @ApiOperation(value = "今年每月巡检工单", notes = "今年每月巡检工单")
    @PostMapping(value = "findMonthCount")
    Map<String,Object> findMonthCount(){
        return cleaningInspectionTasksheetService.findMonthCount();
    }



}
