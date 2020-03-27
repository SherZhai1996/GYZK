package cn.edu.ujs.lp.intells.controller.systemmanagement;

import cn.edu.ujs.lp.intells.common.entity.Serviceitem.Servicebill;
import cn.edu.ujs.lp.intells.common.entity.Serviceitem.ServicebillBrief;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServicebillPageRequest;
import cn.edu.ujs.lp.intells.common.request.Serviceitem.ServicebillSaveRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.ServicebillServiceV2;
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

@Api(description = "服务事项明细接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Servicebill")
public class ServicebillControllerV2 {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private ServicebillServiceV2 servicebillServiceV2;

    @ApiOperation(value = "下载服务事项明细Excel模板",notes = "下载服务事项明细Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return servicebillServiceV2.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"服务事项明细Excel模板下载失败"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传服务事项明细Excel文件",notes = "上传服务事项明细Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            return servicebillServiceV2.ImportExcelData(header.getHeader("hosp-ID"), file);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "服务事项明细Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出服务事项明细Excel数据",notes = "导出服务事项明细Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            return servicebillServiceV2.exportExcelData(response);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出服务事项明细Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有服务事项明细列表",notes = "查询所有服务事项明细列表")
    @ResponseBody
    @PostMapping(value = "/list")
    public JsonResponse list(@RequestBody @Valid ServicebillPageRequest request){
        List<ServicebillBrief> result;

        try {
            result = servicebillServiceV2.list(request);
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有服务事项明细列表成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ServicebillBrief> page(@RequestBody @Valid ServicebillPageRequest request) {
        try {
            PageResponse response = servicebillServiceV2.page(request);
            return JsonResponse.success("服务事项明细分页查询成功", response);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "服务事项明细分页查询失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定ID的服务事项明细完整信息",notes = "获取指定ID的服务事项明细完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (servicebillServiceV2 != null)
        {
            try {
                Servicebill servicebill = servicebillServiceV2.getByID(id);
                return JsonResponse.success("获取指定ID的服务事项明细完整信息成功",servicebill);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的服务事项明细完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的服务事项明细",notes = "删除指定ID的服务事项明细")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = servicebillServiceV2.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的服务事项明细信息成功",id);
    }

    @ApiOperation(value = "保存一个服务事项明细信息，可以是新增或更新",notes = "保存一个服务事项明细信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ServicebillBrief> save(@RequestBody @Valid ServicebillSaveRequest request)
    {
        Servicebill servicebill = null;

        try {
            servicebill = servicebillServiceV2.save(request);

            return JsonResponse.success("保存服务事项明细信息成功", servicebill);
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存服务事项明细失败："+e.getMessage());
        }

    }
}
