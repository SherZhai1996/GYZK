package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicebill;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicePDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.DevicebillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(description = "设备明细接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Devicebill")
public class DevicebillController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private DevicebillService devicebillService;


    @ApiOperation(value = "输出选中设备的二维码标签",notes = "输出选中设备的二维码标签")
    @ResponseBody
    @PostMapping(value = "/deviceQrCode")
    public JsonResponse deviceQrCode(@RequestBody @Valid DevicePDFRequest devicePDFRequest, HttpServletResponse response){
        if ((header != null) && (devicebillService != null)) {
            try {
                return devicebillService.ExportDevicePdf(header.getHeader("hosp-ID"), devicePDFRequest, response);
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
            return devicebillService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"设备明细Excel模板下载失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传设备明细Excel文件",notes = "上传设备明细Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return devicebillService.ImportExcelData(header.getHeader("hosp-ID"), file);
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
                return devicebillService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出设备明细Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出设备明细Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有设备明细",notes = "查询所有设备明细")
    @ResponseBody
    @GetMapping(value = "/list/{categoryId}")
    public JsonResponse list(@PathVariable("categoryId") String categoryId){
        List<DevicebillBrief> result;

        try {
            if (header != null)
                result = devicebillService.list(header.getHeader("hosp-ID"), categoryId.trim());
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有设备明细失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有设备明细信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<DevicebillBrief> page(@RequestBody @Valid DevicebillPageRequest request)
    {
        try {
            if (header != null) {
                PageResponse response = devicebillService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("设备明细分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，设备明细分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "设备明细分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的设备明细完整信息",notes = "获取指定ID的设备明细完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (devicebillService != null)
        {
            try {
                Devicebill db = devicebillService.getByID(id);
                return JsonResponse.success("获取指定ID的设备明细完整信息成功",db);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的设备明细完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的设备明细",notes = "删除指定ID的设备明细")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = devicebillService.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的设备明细信息成功",id);
    }

    @ApiOperation(value = "保存一个设备明细信息，可以是新增或更新",notes = "保存一个设备明细信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<DevicebillBrief> save(@RequestBody @Valid DevicebillSaveRequest request)
    {
        Devicebill devicebill = null;

        try {
            if (header != null) {
                devicebill = devicebillService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存设备明细信息成功", devicebill);
            } else
                return JsonResponse.fail(1001, "没有指定医院，保存设备明细失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存设备明细失败："+e.getMessage());
        }

    }

}
