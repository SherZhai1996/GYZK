package cn.edu.ujs.lp.intells.controller.deviceinspection;


import cn.edu.ujs.lp.intells.operationmaintenance.entity.deviceinspection.DeviceInspectionItemRadioItem;
import cn.edu.ujs.lp.intells.operationmaintenance.request.deviceinspection.DeviceInspectionItemRadioItemRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.deviceinspection.DeviceInspectionItemRadioItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "设备选项单选类型表")
@CrossOrigin
@RestController
@RequestMapping(value = "/deviceinspection-itemradioitem")
public class DeviceInspectionItemRadioItemController {
    @Autowired
    DeviceInspectionItemRadioItemService deviceInspectionItemRadioItemService;

    @ApiOperation(value = "新增设备选项单选类型",notes = "新增设备选项单选类型")
    @PostMapping(value = "/add")
    public Object add(@RequestBody @Valid DeviceInspectionItemRadioItem request){
        return deviceInspectionItemRadioItemService.add(request);
    }


    @ApiOperation(value = "更新设备选项单选类型",notes = "更新设备选项单选类型")
    @PostMapping(value = "/update")
    public Object update(@RequestBody @Valid DeviceInspectionItemRadioItem request){
        return deviceInspectionItemRadioItemService.update(request);
    }


    @ApiOperation(value = "删除设备选项单选类型", notes = "删除设备选项单选类型")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceInspectionItemRadioItem request){
        return deviceInspectionItemRadioItemService.delete(request.getId());
    }

    @ApiOperation(value = "根据项目内容id查找项目选项类型",notes = "根据项目内容id查找项目选项类型")
    @PostMapping(value = "findByItemcontentId")
    public Object findByItemcontentId(@RequestBody @Valid DeviceInspectionItemRadioItemRequest request){
        return  deviceInspectionItemRadioItemService.findByItemcontentId(request);
    }



}
