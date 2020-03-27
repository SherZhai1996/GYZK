package cn.edu.ujs.lp.intells.controller.devicemanage;


import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.operationmaintenance.entity.devicemanage.DeviceCategory;
import cn.edu.ujs.lp.intells.operationmaintenance.request.devicemanage.DeviceCategoryRequest;
import cn.edu.ujs.lp.intells.operationmaintenance.service.devicemanage.DeviceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(description = "设备类别")
@CrossOrigin
@RestController
@RequestMapping(value = "/devicecategory")
public class DeviceCategoryController {

    @Autowired
    private DeviceCategoryService deviceCategoryService;

    @Autowired
    private HttpServletRequest header;

    @ApiOperation(value = "新建设备类别",notes = "新建设备类别")
    @PostMapping(value = "add")
    public Object add(@RequestBody @Valid DeviceCategory request) throws Exception{
        return deviceCategoryService.add(request);
    }

    @ApiOperation(value = "查找设备类别", notes = "查找设备类别")
    @PostMapping(value = "findAll")
    public Object findAll(){
        return deviceCategoryService.findAll();
    }

    @ApiOperation(value = "删除设备类别",notes = "删除设备类别")
    @PostMapping(value = "delete")
    public Object delete(@RequestBody @Valid DeviceCategory request){
        return  deviceCategoryService.delete(request.getId());
    }

    @ApiOperation(value = "修改设备类别")
    @PostMapping(value = "update")
    public Object update(@RequestBody @Valid DeviceCategory request){
        return deviceCategoryService.update(request);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @PostMapping(value = "findByPage")
    public Object findByPage(@RequestBody DeviceCategoryRequest request){
        return deviceCategoryService.findByPage(request);
    }

    @ApiOperation(value = "按设备类别查询",notes = "按设备类别查询")
    @PostMapping(value = "findByCategory")
    public Object findByCategory(@RequestBody DeviceCategoryRequest request){
        return deviceCategoryService.findByCategory(request);
    }

    @ApiOperation(value = "更新责任归属",notes = "更新责任归属")
    @PostMapping(value = "updateResponsibility")
    public Object updateResponsibility (@RequestBody DeviceCategoryRequest request){
        return deviceCategoryService.updateResponsibility(request);
    }

    @ApiOperation(value = "更新管理归属",notes = "更新管理归属")
    @PostMapping(value = "updateManage")
    public Object updateManage(@RequestBody DeviceCategoryRequest request){
        return deviceCategoryService.updateManage(request);
    }


    @ApiOperation(value = "查询树形结构",notes = "查询树形结构")
    @PostMapping(value = "getJson")
    public Object getJson(){
        return deviceCategoryService.getJson();
    }



    @ApiOperation(value = "下载设备运维分类Excel模板",notes = "下载设备运维分类Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return deviceCategoryService.exportExcelTemplate(response);
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
                return deviceCategoryService.ImportExcelData(header.getHeader("hosp-ID"), file);
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
                return deviceCategoryService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出设备运维分类Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出设备运维分类Excel数据失败:" + e.getMessage());
        }
    }

}
