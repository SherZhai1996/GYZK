package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import cn.edu.ujs.lp.intells.basicinformation.entity.Device.Devicecategory;
import cn.edu.ujs.lp.intells.common.entity.Device.DevicecategoryBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicecategoryPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Device.DevicecategorySaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.DevicecategoryService;
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

@Api(description = "设备运维分类接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Devicecategory")
public class DevicecategoryController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    private DevicecategoryService devicecategoryService;

    @ApiOperation(value = "下载设备运维分类Excel模板",notes = "下载设备运维分类Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return devicecategoryService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"设备运维分类Excel模板下载失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传设备运维分类Excel文件",notes = "上传设备运维分类Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return devicecategoryService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，设备运维分类Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "设备运维分类Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出设备运维分类Excel数据",notes = "导出设备运维分类Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return devicecategoryService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出设备运维分类Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出设备运维分类Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有设备运维分类",notes = "查询所有设备运维分类")
    @ResponseBody
    @GetMapping(value = "/list/{categoryName}")
    public JsonResponse list(@PathVariable("categoryName") String categoryName){
        List<DevicecategoryBrief> result;

        try {
            if (header != null)
                result = devicecategoryService.list(header.getHeader("hosp-ID"), categoryName.trim());
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有设备运维分类失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有设备运维分类信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<DevicecategoryBrief> page(@RequestBody @Valid DevicecategoryPageRequest request)
    {
        try {
            if (header != null) {
                PageResponse response = devicecategoryService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的设备运维分类树形结构",notes = "获取指定ID的设备运维分类树形结构")
    @ResponseBody
    @GetMapping(value = "/getDevicecategoryTreeStr/{categoryName}")
    public JsonResponse getDevicecategoryTreeStr(@PathVariable("categoryName") String categoryName){

        if ((header != null) && (devicecategoryService != null))
        {
            try {
                List<Object> lst = devicecategoryService.getDevicecategoryTreeStr(header.getHeader("hosp-ID"),categoryName);

                return JsonResponse.success("获取指定ID的设备运维分类树形结构成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的设备运维分类树形结构失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的设备运维分类树形结构失败");
    }

    @ApiOperation(value = "获取指定ID的设备运维分类完整信息",notes = "获取指定ID的设备运维分类完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (devicecategoryService != null)
        {
            try {
                Devicecategory ec = devicecategoryService.getByID(id);
                return JsonResponse.success("获取指定ID的设备运维分类完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的设备运维分类完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的设备运维分类",notes = "删除指定ID的设备运维分类")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = devicecategoryService.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的设备运维分类信息成功",id);
    }

    @ApiOperation(value = "保存一个设备运维分类信息，可以是新增或更新",notes = "保存一个设备运维分类信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<DevicecategoryBrief> save(@RequestBody @Valid DevicecategorySaveRequest request)
    {
        Devicecategory devicecategory = null;

        try {
            if (header != null) {
                devicecategory = devicecategoryService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存设备运维分类信息成功", devicecategory);
            }else
                return JsonResponse.fail(1001, "没有指定医院，保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }
    }
}
