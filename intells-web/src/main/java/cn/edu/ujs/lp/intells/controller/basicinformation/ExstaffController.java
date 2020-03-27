package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExstaffBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExstaffPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExstaffSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.ExstaffService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(description = "外委职工接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Exstaff")
public class ExstaffController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    private ExstaffService exstaffService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载外委职工Excel模板",notes = "下载外委职工Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return exstaffService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"外委职工Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传外委职工Excel文件",notes = "上传外委职工Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return exstaffService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，外委职工Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "外委职工Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出外委职工Excel数据",notes = "导出外委职工Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return exstaffService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出外委职工Excel数据失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出外委职工Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有外委职工",notes = "查询所有外委职工")
    @ResponseBody
    @GetMapping(value = "/list/{excompanyID}/{exteamId}")
    public JsonResponse list(
            @ApiParam(value = "外委公司ID，支持模糊查询", required = false)
            @PathVariable("excompanyID") String excompanyID,
            @ApiParam(value = "服务班组ID，支持模糊查询", required = false)
            @PathVariable("exteamId") String exteamId) {
        List<ExstaffBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getExstaffs(header.getHeader("hosp-ID"), excompanyID.trim(), exteamId.trim());
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有外委职工失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有外委职工信息成功", result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ExstaffBrief> page(@RequestBody @Valid ExstaffPageRequest request) {
        try {
            if (header != null) {
                PageResponse response = exstaffService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，分页查询失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的外委职工完整信息",notes = "获取指定ID的外委职工完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){
        if (exstaffService != null)
        {
            try {
                Exstaff ec = exstaffService.getByID(id);
                return JsonResponse.success("获取指定ID的外委职工完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的外委职工完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的外委职工",notes = "删除指定ID的外委职工")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id) {
        boolean result;

        try {
            result = exstaffService.delete(id);
        } catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("删除指定ID的外委职工信息成功", id);
    }


    @ApiOperation(value = "保存一个服务班组信息，可以是新增或更新",notes = "保存一个服务班组信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ExstaffBrief> save(@RequestBody @Valid ExstaffSaveRequest request) {
        Exstaff exstaff = null;

        try {
            if (header != null) {
                exstaff = exstaffService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存外委职工信息成功", exstaff);
            } else
                return JsonResponse.fail(1001, "没有指定医院，保存失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }
    }


    @ApiOperation(value = "上传外委职工照片文件",notes = "上传外委职工照片文件")
    @ResponseBody
    @PostMapping("/uploadExstaff_picture/{exstaffId}")
    public JsonResponse uploadExstaff_picture(@PathVariable("exstaffId") String exstaffId,@RequestParam("file") MultipartFile file) {
        try {
            if ((header != null) && (exstaffService != null))
                return exstaffService.uploadExstaff_picture(header.getHeader("hosp-ID"), exstaffId, file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传医院外委职工照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "上传外委职工照片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除外委职工照片文件",notes = "删除外委职工照片文件")
    @ResponseBody
    @PostMapping("/clearExstaff_picture/{exstaffId}")
    public JsonResponse clearExstaff_picture(@PathVariable("exstaffId") String exstaffId) {
        try {
            if ((header != null) && (exstaffService != null))
                return exstaffService.clearExstaff_picture(exstaffId);
            else
                return JsonResponse.fail(1009, "没有指定医院，删除外委职工照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "删除外委职工照片失败:" + e.getMessage());
        }
    }


    @ApiOperation(value = "上传外委职工技术证书照片文件",notes = "上传外委职工技术证书照片文件")
    @ResponseBody
    @PostMapping("/uploadExstaff_skillcertificate_pic/{exstaffId}/{skillcertificateId}")
    public JsonResponse uploadExstaff_skillcertificate_pic(@PathVariable("exstaffId") String exstaffId,@PathVariable("skillcertificateId") String skillcertificateId,@RequestParam("file") MultipartFile file) {
        try {
            if ((header != null) && (exstaffService != null))
                return exstaffService.uploadExstaff_skillcertificate_pic(header.getHeader("hosp-ID"), exstaffId, skillcertificateId, file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传外委职工技术证书照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "上传外委职工技术证书照片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除外委职工技术证书照片文件",notes = "删除外委职工技术证书照片文件")
    @ResponseBody
    @PostMapping("/clearExstaff_skillcertificate_pic/{exstaffId}/{skillcertificateId}")
    public JsonResponse clearExstaff_skillcertificate_pic(@PathVariable("exstaffId") String exstaffId,@PathVariable("skillcertificateId") String skillcertificateId) {
        try {
            if ((header != null) && (exstaffService != null))
                return exstaffService.clearExstaff_skillcertificate_pic(exstaffId, skillcertificateId);
            else
                return JsonResponse.fail(1009, "没有指定医院，删除外委职工技术证书照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "删除外委职工技术证书照片失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "清除外委职工所有技术证书照片",notes = "清除外委职工所有技术证书照片")
    @ResponseBody
    @PostMapping("/clearExstaff_Allskillcertificate_pic/{exstaffId}")
    public JsonResponse clearExstaff_Allskillcertificate_pic(@PathVariable("exstaffId") String exstaffId) {
        try {
            if ((header != null) && (exstaffService != null))
                return exstaffService.clearExstaff_Allskillcertificate_pic(exstaffId);
            else
                return JsonResponse.fail(1009, "没有指定医院，清除外委职工所有技术证书照片失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "清除外委职工所有技术证书照片失败:" + e.getMessage());
        }
    }

}
