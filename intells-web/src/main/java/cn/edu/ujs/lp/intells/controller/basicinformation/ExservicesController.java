package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.ExServices;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExServicesBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExServicesPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExServicesSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.ExServicesService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
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

@Api(description = "业务部门接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Exservices")
public class ExservicesController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private ExServicesService exServicesService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载业务部门Excel模板",notes = "下载业务部门Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return exServicesService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"业务部门Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传业务部门Excel文件",notes = "上传业务部门Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return exServicesService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院,业务部门Excel文件失败!");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "业务部门Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出业务部门Excel数据",notes = "导出业务部门Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return exServicesService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院,导出业务部门Excel数据失败!");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出业务部门Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有业务部门",notes = "查询所有业务部门")
    @ResponseBody
    @GetMapping(value = "/list/{excompanyID}")
    public JsonResponse list(@PathVariable("excompanyID") String excompanyID){
        List<ExServicesBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getExservices(header.getHeader("hosp-ID"), excompanyID.trim());
            else
                return JsonResponse.fail(1001, "没有指定医院，查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有业务部门信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ExServicesBrief> page(@RequestBody @Valid ExServicesPageRequest request)
    {
        try {
            if (header != null) {
                PageResponse response = exServicesService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，分页查询失败！");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的业务部门完整信息",notes = "获取指定ID的业务部门完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (exServicesService != null)
        {
            try {
                ExServices ec = exServicesService.getByID(id);
                return JsonResponse.success("获取指定ID的业务部门完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的业务部门完整信息失败,服务为null");
    }


    @ApiOperation(value = "删除指定ID的业务部门",notes = "删除指定ID的业务部门")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = exServicesService.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的业务部门信息成功",id);
    }

    @ApiOperation(value = "保存一个业务部门信息，可以是新增或更新",notes = "保存一个业务部门信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ExServicesBrief> save(@RequestBody @Valid ExServicesSaveRequest request)
    {
        ExServices exServices = null;

        try {
            if (header != null) {
                exServices = exServicesService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存业务部门信息成功", exServices);
            } else
                return JsonResponse.fail(1001, "没有指定医院，保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }
    }
}
