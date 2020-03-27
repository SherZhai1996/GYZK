package cn.edu.ujs.lp.intells.controller.systemmanagement;

import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicebill;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicePDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicebillSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.DevicebillService;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicebillBrief;
import cn.edu.ujs.lp.intells.common.entity.Device.IbeaconBrief;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.systemmanagement.entity.Ibeacon;
import cn.edu.ujs.lp.intells.systemmanagement.request.IbeaconPageRequest;
import cn.edu.ujs.lp.intells.systemmanagement.request.IbeaconSaveRequest;
import cn.edu.ujs.lp.intells.systemmanagement.service.IbeaconService;
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

@Api(description = "蓝牙信标接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Ibeacon")
public class IbeaconController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private IbeaconService ibeaconService;

    @ApiOperation(value = "下载蓝牙信标Excel模板",notes = "下载蓝牙信标Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return ibeaconService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"蓝牙信标Excel模板下载失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传蓝牙信标Excel文件",notes = "上传蓝牙信标Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            return ibeaconService.ImportExcelData(header.getHeader("hosp-ID"), file);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "蓝牙信标Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出蓝牙信标Excel数据",notes = "导出蓝牙信标Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            return ibeaconService.exportExcelData(header.getHeader("hosp-ID"), response);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出蓝牙信标Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有蓝牙信标",notes = "查询所有蓝牙信标")
    @ResponseBody
    @GetMapping(value = "/list/{ibeaconName}")
    public JsonResponse list(@PathVariable("ibeaconName") String ibeaconName){
        List<IbeaconBrief> result;

        try {
            result = ibeaconService.list(header.getHeader("hosp-ID"), ibeaconName.trim());
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有蓝牙信标信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<IbeaconBrief> page(@RequestBody @Valid IbeaconPageRequest request) {
        try {
            PageResponse response = ibeaconService.page(header.getHeader("hosp-ID"), request);
            return JsonResponse.success("蓝牙信标分页查询成功", response);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "蓝牙信标分页查询失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定ID的蓝牙信标完整信息",notes = "获取指定ID的蓝牙信标完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (ibeaconService != null)
        {
            try {
                Ibeacon db = ibeaconService.getByID(id);
                return JsonResponse.success("获取指定ID的蓝牙信标完整信息成功",db);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的蓝牙信标完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的蓝牙信标",notes = "删除指定ID的蓝牙信标")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = ibeaconService.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的蓝牙信标信息成功",id);
    }

    @ApiOperation(value = "修改指定ID的蓝牙信标状态",notes = "修改指定ID的蓝牙信标状态")
    @ResponseBody
    @GetMapping(value = "/enableIbeacon/{id}/{usingflag}")
    public JsonResponse enableIbeacon(@PathVariable("id") String id,@PathVariable("usingflag") String usingflag){
        boolean result;

        try {
            result = ibeaconService.enableIbeacon(id,usingflag);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,"修改指定ID的蓝牙信标状态失败："+e.getMessage());
        }

        return JsonResponse.success("修改指定ID的蓝牙信标状态成功",id);
    }

    @ApiOperation(value = "保存一个蓝牙信标信息，可以是新增或更新",notes = "保存一个蓝牙信标信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<IbeaconBrief> save(@RequestBody @Valid IbeaconSaveRequest request)
    {
        Ibeacon ibeacon = null;

        try {
            ibeacon = ibeaconService.save(header.getHeader("hosp-ID"), request);

            return JsonResponse.success("保存蓝牙信标信息成功", ibeacon);
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存蓝牙信标失败："+e.getMessage());
        }

    }

}
