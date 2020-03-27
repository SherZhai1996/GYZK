package cn.edu.ujs.lp.intells.controller.systemmanagement;

import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Serviceitemcategory;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServiceitemcategoryBrief;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServiceitemcategoryPageRequest;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServiceitemcategorySaveRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.ServiceitemcategoryServiceV2;
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

@Api(description = "服务事项分类接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Serviceitemcategory")
public class ServiceitemcategoryControllerV2 {
    @Autowired
    private HttpServletRequest header;

    @Autowired
    private ServiceitemcategoryServiceV2 serviceitemcategoryServiceV2;

    @ApiOperation(value = "下载服务事项分类Excel模板",notes = "下载服务事项分类Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return serviceitemcategoryServiceV2.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"服务事项分类Excel模板下载失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传服务事项分类Excel文件",notes = "上传服务事项分类Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            return serviceitemcategoryServiceV2.ImportExcelData(header.getHeader("hosp-ID"), file);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "服务事项分类Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出服务事项分类Excel数据",notes = "导出服务事项分类Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            return serviceitemcategoryServiceV2.exportExcelData(response);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出服务事项分类Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有服务事项分类列表",notes = "查询所有服务事项分类列表")
    @ResponseBody
    @PostMapping(value = "/list")
    public JsonResponse list(@RequestBody @Valid ServiceitemcategoryPageRequest request){
        List<ServiceitemcategoryBrief> result;

        try {
            result = serviceitemcategoryServiceV2.list(request);
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有服务事项分类列表成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ServiceitemcategoryBrief> page(@RequestBody @Valid ServiceitemcategoryPageRequest request) {
        try {
            PageResponse response = serviceitemcategoryServiceV2.page(request);
            return JsonResponse.success("服务事项分类分页查询成功", response);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "服务事项分类分页查询失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定ID的服务事项分类完整信息",notes = "获取指定ID的服务事项分类完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (serviceitemcategoryServiceV2 != null)
        {
            try {
                Serviceitemcategory serviceitemcategory = serviceitemcategoryServiceV2.getByID(id);
                return JsonResponse.success("获取指定ID的服务事项分类完整信息成功",serviceitemcategory);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的服务事项分类完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的服务事项分类",notes = "删除指定ID的服务事项分类")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = serviceitemcategoryServiceV2.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的服务事项分类信息成功",id);
    }

    @ApiOperation(value = "保存一个服务事项分类信息，可以是新增或更新",notes = "保存一个服务事项分类信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ServiceitemcategoryBrief> save(@RequestBody @Valid ServiceitemcategorySaveRequest request)
    {
        Serviceitemcategory serviceitemcategory = null;

        try {
            serviceitemcategory = serviceitemcategoryServiceV2.save(request);

            return JsonResponse.success("保存服务事项分类信息成功", serviceitemcategory);
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存服务事项分类失败："+e.getMessage());
        }

    }

}
