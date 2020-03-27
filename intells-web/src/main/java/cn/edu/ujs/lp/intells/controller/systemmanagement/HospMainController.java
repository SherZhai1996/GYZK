package cn.edu.ujs.lp.intells.controller.systemmanagement;

import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.systemmanagement.request.HospMainSaveRequest;
import cn.edu.ujs.lp.intells.common.request.Hosp.HospPageRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.systemmanagement.service.HospMainService;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospMain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 医院服务接口类
 */
@Api(description = "医院服务接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Hospmain")
public class HospMainController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    HospMainService hospMainService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "上传医院标志性图片文件",notes = "上传医院标志性图片文件")
    @ResponseBody
    @PostMapping("/uploadHosp_picture")
    public JsonResponse uploadHosp_picture(@RequestParam("file") MultipartFile file){
        try {
            if ((header != null) && (hospMainService != null))
                return hospMainService.uploadHosp_picture(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传医院标志性图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传医院标志性图片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除医院标志性图片文件",notes = "删除医院标志性图片文件")
    @ResponseBody
    @PostMapping("/clearHosp_picture")
    public JsonResponse clearHosp_picture(){
        try {
            if ((header != null) && (hospMainService != null))
                return hospMainService.clearHosp_picture(header.getHeader("hosp-ID"));
            else
                return JsonResponse.fail(1009, "没有指定医院，删除医院标志性图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "删除医院标志性图片失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有医院",notes = "查询所有医院")
    @ApiModelProperty(value = "医院名称",name = "hospName",example="南京中医院")
    @ResponseBody
    @GetMapping(value = "/list/{hospName}")
    public JsonResponse list(@PathVariable("hospName") String hospName){
        List<HospBrief> result;

        try {
            if (dbCommonService != null)
                result = dbCommonService.getHosps(hospName.trim());
            else
                return JsonResponse.fail(1001, "询所有医院失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有医院信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<HospBrief> page(@RequestBody @Valid HospPageRequest request)
    {
        try {
            if (hospMainService != null) {
                PageResponse response = hospMainService.page(request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定ID的医院完整信息",notes = "获取指定ID的医院完整信息")
    @ResponseBody
    @GetMapping(value = "/getHospMainbyID")
    public JsonResponse getHospMainbyID(){

        if ((header != null) && (hospMainService != null))
        {
            try {
                HospMain hospMain = hospMainService.getHospMainbyID(header.getHeader("hosp-ID"),null);
                return JsonResponse.success("获取指定ID的医院完整信息成功",hospMain);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的医院完整信息失败");
    }

    @ApiOperation(value = "删除指定ID的医院",notes = "删除指定ID的医院")
    @ResponseBody
    @GetMapping(value = "/delete")
    public JsonResponse delete(){
        boolean result;

        try {
            if ((header != null) && (hospMainService != null)) {
                result = hospMainService.delete(header.getHeader("hosp-ID"), null);
            }
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的医院信息成功");
    }

    @ApiOperation(value = "保存一个医院管理信息，可以是新增或更新",notes = "保存一个医院管理信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/saveHospMain")
    public JsonResponse<HospMain> saveHospMain(@RequestBody @Valid HospMainSaveRequest request)
    {
        HospMain hospmain = null;

        try {
            if (hospMainService!= null) {
                hospmain = hospMainService.saveHospMain(request);
            }
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存医院管理信息失败:"+e.getMessage());
        }

        if (hospmain != null)
        {
            return JsonResponse.success("保存医院管理成功", hospmain);
        }
        else
            return JsonResponse.fail(1002,"保存医院管理信息失败!");

    }
}
