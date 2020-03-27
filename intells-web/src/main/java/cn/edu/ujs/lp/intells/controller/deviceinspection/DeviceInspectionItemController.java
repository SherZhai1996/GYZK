package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionItem;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionItemRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备巡检项目")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-item")
public class DeviceInspectionItemController {
    @Autowired
    DeviceInspectionItemService deviceInspectionItemService;

    @ApiOperation(value = "新增保洁巡检项目" ,notes="新增保洁巡检项目")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionItem request){
        return deviceInspectionItemService.add(request);
    }

    @ApiOperation(value = "更新保洁巡检项目",notes = "更新保洁巡检项目")
    @PostMapping(value = "/update")
    public Object update(@RequestBody @Valid DeviceInspectionItem request){
        return deviceInspectionItemService.update(request);
    }

    @ApiOperation(value = "删除保洁巡检项目", notes = "删除保洁巡检项目")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionItem request){
        return deviceInspectionItemService.delete(request.getId());
    }

    @ApiOperation(value = "根据模板id查询", notes = "条根据模板id查询")
    @PostMapping(value = "findByTemplateId")
    public Object findByTemplateId(@RequestBody DeviceInspectionItemRequest request){
        Map tableList = deviceInspectionItemService.templateList(request);
        return tableList;
    }
}
