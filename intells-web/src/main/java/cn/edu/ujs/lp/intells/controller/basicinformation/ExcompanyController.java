package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Excompany;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExcompanyBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExcompanyPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExcompanySaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.ExcompanyService;
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

@Api(description = "外委公司接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Excompany")
public class ExcompanyController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private ExcompanyService excompanyService;

    @Autowired
    private DBCommonService dbCommonService;


    @ApiOperation(value = "上传营业执照图片文件",notes = "上传营业执照图片文件")
    @ResponseBody
    @PostMapping("/uploadCertificate/{excompanyId}")
    public JsonResponse uploadCertificate(@PathVariable("excompanyId") String excompanyId,@RequestParam("file") MultipartFile file){
        try {
            if ((header != null) && (excompanyService != null))
                return excompanyService.uploadCertificate(header.getHeader("hosp-ID"),excompanyId, file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传营业执照图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传营业执照图片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除营业执照图片文件",notes = "删除营业执照图片文件")
    @ResponseBody
    @PostMapping("/clearCertificate/{excompanyId}")
    public JsonResponse clearCertificate(@PathVariable("excompanyId") String excompanyId){
        try {
            if ((header != null) && (excompanyService != null))
                return excompanyService.clearCertificate(header.getHeader("hosp-ID"),excompanyId);
            else
                return JsonResponse.fail(1009, "没有指定医院，删除营业执照图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "删除营业执照图片失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "下载外委公司Excel模板",notes = "下载外委公司Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return excompanyService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"外委公司Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传外委公司Excel文件",notes = "上传外委公司Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return excompanyService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没指定医院ID，上传外委公司Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传外委公司Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出外委公司Excel数据",notes = "导出外委公司Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return excompanyService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没指定医院，导出外委公司Excel数据失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出外委公司Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有外委公司",notes = "查询所有外委公司")
    @ResponseBody
    @GetMapping(value = "/list")
    public JsonResponse list(){
        List<ExcompanyBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getExcompanys(header.getHeader("hosp-ID"));
            else
                return JsonResponse.fail(1001, "没指定医院，查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有外委公司信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ExcompanyBrief> page(@RequestBody @Valid ExcompanyPageRequest request)
    {
        try {

            if (header != null) {
                PageResponse response = excompanyService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没指定医院，分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的外委公司完整信息",notes = "获取指定ID的外委公司完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (excompanyService != null)
        {
            try {
                Excompany ec = excompanyService.getByID(id);
                return JsonResponse.success("获取指定ID的外委公司完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的外委公司完整信息失败,服务为null");
    }


    @ApiOperation(value = "删除指定ID的外委公司",notes = "删除指定ID的外委公司")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id){
        boolean result;

        try {
            result = excompanyService.delete(id);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的外委公司信息成功",id);
    }

    @ApiOperation(value = "保存一个外委公司信息，可以是新增或更新",notes = "保存一个外委公司信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ExcompanyBrief> save(@RequestBody @Valid ExcompanySaveRequest request)
    {
        Excompany excompany = null;

        try {
            if (header != null) {
                excompany = excompanyService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存外围公司信息成功", excompany);
            } else
                return JsonResponse.fail(1001, "没指定医院，保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }
    }
}
