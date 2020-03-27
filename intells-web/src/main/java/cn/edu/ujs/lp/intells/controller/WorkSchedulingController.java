package cn.edu.ujs.lp.intells.controller;


import cn.edu.ujs.lp.intells.servicecenter.entity.TodaySchedule;
import cn.edu.ujs.lp.intells.servicecenter.entity.WorkScheduling;
import cn.edu.ujs.lp.intells.servicecenter.request.*;
import cn.edu.ujs.lp.intells.servicecenter.service.WorkSchedulingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(description = "排班接口")
@CrossOrigin
@RestController
@RequestMapping(value = "/workingscheduling")
public class WorkSchedulingController {
    @Autowired
    WorkSchedulingService workSchedulingService;

    @PostMapping(value = "/findAll")
    @ApiOperation(value = "分页查询时间表所有数据" ,  notes="分页查询时间表所有数据")
    public Map<String,Object> findAll(@RequestBody WorkSchedulingRequest request){
        return workSchedulingService.findAll(request);
    }
    @PostMapping(value = "/insertInto")
    @ApiOperation(value = "插入时间表数据" ,  notes="插入时间表数据")
    public Map<String,Object> insertInto(@RequestBody WorkSchedulingRequest record  ){
        return workSchedulingService.insertInto( record);
    }
    @PostMapping(value = "/deleteById")
    @ApiOperation(value = "删除一条时间表数据" ,  notes="删除一条时间表数据")
    public Map<String,Object> deleteById(@RequestBody WorkScheduling record  ){
        return workSchedulingService.deleteById( record);
    }
    @PostMapping(value = "/selectexTeamId")
    @ApiOperation(value = "查找班组id" ,  notes="查找班组id")
    public Map<String,Object> selectexTeamId(@RequestBody WorkScheduling request  ){
        return workSchedulingService.selectexTeamId( request.getId());
    }
    @PostMapping(value = "/insertIntoScheduleAndWorkGrid")
    @ApiOperation(value = "插入排班和班次网格" ,  notes="插入排班和班次网格")
    public Map<String,Object> insertIntoScheduleAndWorkGrid(@RequestBody ScheduleWorkRequest request  ){
        return workSchedulingService.insertIntoScheduleAndWorkGrid( request);
    }
    @PostMapping(value = "/selectIdAndClasses")
    @ApiOperation(value = "查找班次id和名称" ,  notes="查找班次id和名称")
    public Map<String,Object> selectIdAndClasses(@RequestBody ScheduleWorkRequest request  ){
        return workSchedulingService.selectIdAndClasses( request);
    }
    @PostMapping(value = "/findBycondition")
    @ApiOperation(value = "查找排班表" ,  notes="查找排班表")
    public Map<String,Object> findBycondition( @RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.findBycondition(request );
    }
    @PostMapping(value = "/selectExteamStaffId")
    @ApiOperation(value = "查找排班表上班人员" ,  notes="查找排班表上班人员")
    public Map<String,Object> selectExteamStaffId( @RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.selectExteamStaffId(request );
    }
    @PostMapping(value = "/selectGridId")
    @ApiOperation(value = "查找排班表上班地点" ,  notes="查找排班表上班地点")
    public Map<String,Object> selectGridId( @RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.selectGridId(request );
    }

    @PostMapping(value = "/deleteByScheduWorkId")
    @ApiOperation(value = "删除排班表数据" ,  notes="删除排班表数据")
    public Map<String,Object> deleteByScheduWorkId( @RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.deleteByScheduWorkId(request.getId() );
    }
    @PostMapping(value = "/updateByScheduWorkId")
    @ApiOperation(value = "更新排班表数据" ,  notes="更新排班表数据")
    public Map<String,Object> updateByScheduWorkId( @RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.updateByScheduWorkId(request );
    }
    @PostMapping(value = "/copyFontDay")
    @ApiOperation(value = "查找班次id和名称" ,  notes="查找班次id和名称")
    public Map<String,Object> copyFontDay(@RequestBody ScheduleWorkRequest request ){
        return workSchedulingService.copyFontDay(request );
    }
    @PostMapping(value = "/copyYouWantDay")
    @ApiOperation(value = "查找班次id和名称" ,  notes="查找班次id和名称")
    public Map<String,Object> copyYouWantDay(@RequestBody ScheduleWorkRequest request  ){
        return workSchedulingService.copyYouWantDay(request);
    }
    @PostMapping(value = "/findAllTodayScheduleByCondition")
    @ApiOperation(value = "返回全部今日上班表" ,  notes="返回全部今日上班表")
    public Map<String,Object> findAllTodayScheduleByCondition(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.findAllTodayScheduleByCondition(request);
    }
    @PostMapping(value = "/updateTodayScheduleStaff")
    @ApiOperation(value = "更新今日上班表员工" ,  notes="返回全部今日上班表")
    public Map<String,Object> updateTodayScheduleStaff(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.updateTodayScheduleStaff(request);
    }
    @PostMapping(value = "/deleteTodayScheduleStaff")
    @ApiOperation(value = "删除今日上班表员工" ,  notes="返回全部今日上班表")
    public Map<String,Object> deleteTodayScheduleStaff(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.deleteTodayScheduleStaff(request);
    }
    @PostMapping(value = "/updateLeaveTimeAndBackTime")
    @ApiOperation(value = "修改短时间请假信息" ,  notes="修改短时间请假信息")
    public Map<String,Object> updateLeaveTimeAndBackTime(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.updateLeaveTimeAndBackTime(request);
    }
    @PostMapping(value = "/insertIntoAskLeave")
    @ApiOperation(value = "插入请假信息" ,  notes="插入请假信息")
    public Map<String,Object> insertIntoAskLeave(@RequestBody AskLeaveRequest request  ){
        return workSchedulingService.insertIntoAskLeave(request);
    }
    @PostMapping(value = "/selectInformationByAskLeave")
    @ApiOperation(value = "查找请假信息" ,  notes="查找请假信息")
    public Map<String,Object> selectInformationByAskLeave(@RequestBody AskLeaveRequest request  ){
        return workSchedulingService.selectInformationByAskLeave(request);
    }
    @PostMapping(value = "/updateAskLeave")
    @ApiOperation(value = "更新请假信息" ,  notes="更新请假信息")
    public Map<String,Object> updateAskLeave(@RequestBody AskLeaveRequest request  ){
        return workSchedulingService.updateAskLeave(request);
    }
    @PostMapping(value = "/deleteAskLeave")
    @ApiOperation(value = "删除请假信息" ,  notes="删除请假信息")
    public Map<String,Object> deleteAskLeave(@RequestBody AskLeaveRequest request  ){
        return workSchedulingService.deleteAskLeave(request);
    }
    @PostMapping(value = "/deleteTodayScheduleRecordByAskLeaveInformation")
    @ApiOperation(value = "根据请假信息删除今日上班表以及插入记录" ,  notes="根据请假信息删除今日上班表以及插入记录")
    public Map<String,Object> deleteTodayScheduleRecordByAskLeaveInformation(@RequestBody AskLeaveRequest request  ){
        return workSchedulingService.deleteTodayScheduleRecordByAskLeaveInformation(request);
    }
    @PostMapping(value = "/updateTodayScheduleRecordByAskLeaveInformation")
    @ApiOperation(value = "根据老上班员工的请假信息更新今日上班表" ,  notes="根据老上班员工的请假信息更新今日上班表")
    public Map<String,Object> updateTodayScheduleRecordByAskLeaveInformation(@RequestBody AskLeaveExstaffIdRequest request  ){
        return workSchedulingService.updateTodayScheduleRecordByAskLeaveInformation(request);
    }
    @PostMapping(value = "/insertIntoTodaySchedule")
    @ApiOperation(value = "生成今日上班表" ,  notes="生成今日上班表")
    public Map<String,Object> insertIntoTodaySchedule(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.insertIntoTodaySchedule(request);
    }@PostMapping(value = "/deleteTodaySchedule")
    @ApiOperation(value = "删除今日上班表" ,  notes="删除今日上班表")
    public Map<String,Object> deleteTodaySchedule(@RequestBody TodayScheduleRequest request  ){
        return workSchedulingService.deleteTodaySchedule(request);
    }
    @PostMapping(value = "/selectExstaffIdAndNameByExteamId")
    @ApiOperation(value = "根据班组查询上班人员" ,  notes="根据班组查询上班人员")
    public Map<String,Object> selectExstaffIdAndNameByExteamId(@RequestBody ExstaffIdAndNameRequest request  ){
        return workSchedulingService.selectExstaffIdAndNameByExteamId(request);
    }
    @PostMapping(value = "/selectExstaffByService")
    @ApiOperation(value = "根据服务类别选人" ,  notes="根据服务类别选人")
    public Map<String,Object> selectExstaffByService(@RequestBody ExstaffIdAndNameRequest request  ){
        return workSchedulingService.selectExstaffByService(request);
    }

}
