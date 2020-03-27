package cn.edu.ujs.lp.intells.controller.devicemanage;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.devicemanage.DeviceBill;
import cn.edu.ujs.lp.intells.operationmaintenance.request.devicemanage.DeviceBillRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.request.devicemanage.DevicePDFRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DeviceBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Api(description = "设备明细")
@CrossOrigin
@RestController
@RequestMapping(value = "/devicebill")
public class DeviceBillController {
    @Autowired
    private DeviceBillService deviceBillService;
    @Autowired
    private HttpServletRequest header;

    @ApiOperation(value = "新建设备明细",notes = "新建设备明细")
    @PostMapping(value = "add")
    public Object add(@RequestBody @Valid DeviceBill request) throws Exception{
        return deviceBillService.add(request);
    }

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @ResponseBody
    @PostMapping(value = "upload")
    public JsonResponse upload (HttpServletRequest request){
        return deviceBillService.upload(request);
    }

    @ApiOperation(value = "删除图片",notes = "删除图片")
    @ResponseBody
    @PostMapping(value = "clearUpload")
    public JsonResponse clearUpload (@RequestBody @Valid String id){
        return deviceBillService.clearUpload(id);
    }

    @ApiOperation(value = "删除设备明细",notes = "删除设备明细")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceBill request){
        return  deviceBillService.delete(request.getId());
    }

    @ApiOperation(value = "修改设备明细",notes = "修改设备明细")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid DeviceBill request) throws Exception{
        return deviceBillService.update(request);
    }

    @ApiOperation(value = "分页查询设备明细",notes = "分特查询设备明细")
    @PostMapping(value = "findByPage")
    Map<String,Object> findByPage(@RequestBody DeviceBillRequest request){
        return deviceBillService.findByPage(request);
    }

    @ApiOperation(value = "根据设备类别查找设备",notes = "根据设备类别查找设备")
    @PostMapping(value = "findByCategory")
    Map<String,Object> findByCategory(@RequestBody DeviceBillRequest request){
        return deviceBillService.findByCategory(request);
    }

    @ApiOperation(value = "多条件查找设备",notes = "多条件查找设备")
    @PostMapping(value = "findBy")
    Map<String,Object> findBy(@RequestBody DeviceBillRequest request){
        return deviceBillService.findBy(request);
    }


    @ApiOperation(value = "修改任务归属",notes = "修改任务归属")
    @PostMapping(value = "updateTask")
    Map<String,Object> updateTask(@RequestBody DeviceBillRequest request){
        return deviceBillService.updateTask(request);
    }



    @ApiOperation(value = "输出选中设备的二维码标签",notes = "输出选中设备的二维码标签")
    @ResponseBody
    @PostMapping(value = "/deviceQrCode")
    public JsonResponse deviceQrCode(@RequestBody @Valid DevicePDFRequest devicePDFRequest, HttpServletResponse response){
        if ((header != null) && (deviceBillService != null)) {
            try {
                return deviceBillService.ExportDevicePdf(header.getHeader("hosp-ID"), devicePDFRequest, response);
            } catch (Exception e) {
                return JsonResponse.fail(1109, "输出设备二维码标签失败:" + e.getMessage());
            }
        }
        else return JsonResponse.fail(1109,"输出设备二维码标签失败！");
    }



    @ApiOperation(value = "下载设备明细Excel模板",notes = "下载设备明细Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return deviceBillService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"设备明细Excel模板下载失败"+e.getMessage());
        }
    }


    @ApiOperation(value = "上传设备明细Excel文件",notes = "上传设备明细Excel文件")
    @ResponseBody
    @PostMapping("/uploadExcel")
    public JsonResponse uploadExcel(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return deviceBillService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，设备明细Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "设备明细Excel文件失败:" + e.getMessage());
        }
    }


    @ApiOperation(value = "导出设备明细Excel数据",notes = "导出设备明细Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return deviceBillService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出设备明细Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出设备明细Excel数据失败:" + e.getMessage());
        }
    }


}
