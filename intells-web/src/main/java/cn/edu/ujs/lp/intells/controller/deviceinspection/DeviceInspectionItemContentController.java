package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionItem;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionItemContent;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionItemContentRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionItemContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检内容")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-itemcontent")
public class DeviceInspectionItemContentController {

    @Autowired
    DeviceInspectionItemContentService deviceInspectionItemContentService;

    @ApiOperation(value = "新增保洁巡检内容" ,notes="新增保洁巡检内容")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionItemContent request){
        return deviceInspectionItemContentService.add(request);
    }

    @ApiOperation(value = "更新保洁巡检内容" ,notes="更新保洁巡检内容")
    @PostMapping(value = "/update")
    public Object update(@RequestBody @Valid DeviceInspectionItemContent request){
        return deviceInspectionItemContentService.update(request);
    }


    @ApiOperation(value = "按照项目id查找项目内容",notes = "按照项目id查找项目内容")
    @PostMapping(value = "findByItemId")
    public Object findByItemId(@RequestBody DeviceInspectionItemContentRequest request){
        Map tableList = deviceInspectionItemContentService.findByItemId(request);
        return tableList;
    }

    @ApiOperation(value = "删除保洁巡检内容", notes = "删除保洁巡检内容")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionItemContent request){
        return deviceInspectionItemContentService.delete(request.getId());
    }
}
