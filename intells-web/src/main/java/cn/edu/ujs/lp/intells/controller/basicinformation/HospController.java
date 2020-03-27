package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.common.request.Hosp.HospParamsSaveRequest;
import cn.edu.ujs.lp.intells.common.request.Hosp.HospSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.HospService;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import io.swagger.annotations.Api;
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
@RequestMapping(value = "/API/Hosp")
public class HospController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    HospService hospService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "上传院徽图片文件",notes = "上传院徽图片文件")
    @ResponseBody
    @PostMapping("/uploadHosp_badge")
    public JsonResponse uploadHosp_badge(@RequestParam("file") MultipartFile file){
        try {
            if ((header != null) && (hospService != null))
                return hospService.uploadHosp_badge(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传院徽图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传院徽图片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除院徽图片文件",notes = "删除院徽图片文件")
    @ResponseBody
    @PostMapping("/clearHosp_badge")
    public JsonResponse clearHosp_badge(){
        try {
            if ((header != null) && (hospService != null))
                return hospService.clearHosp_badge(header.getHeader("hosp-ID"));
            else
                return JsonResponse.fail(1009, "没有指定医院，删除院徽图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "删除院徽图片失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定ID的医院完整信息",notes = "获取指定ID的医院完整信息")
    @ResponseBody
    @GetMapping(value = "/getHospbyID")
    public JsonResponse getHospbyID(){

        if ((header != null) && (hospService != null))
        {
            try {
                Hosp hosp = hospService.getHospbyID(header.getHeader("hosp-ID"),null);
                return JsonResponse.success("获取指定ID的医院完整信息成功",hosp);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的医院完整信息失败");
    }

    @ApiOperation(value = "保存一个医院信息，可以是新增或更新",notes = "保存一个医院信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/saveHosp")
    public JsonResponse<Hosp> saveHosp(@RequestBody @Valid HospSaveRequest request)
    {
        Hosp hosp = null;

        try {
            if ((header != null) && (hospService != null)) {
                hosp = hospService.saveHosp(header.getHeader("hosp-ID"), request);
            }

        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        if (hosp != null)
        {
            return JsonResponse.success("保存医院成功", hosp);
        }
        else
            return JsonResponse.fail(1001,"保存医院信息失败!");

    }

    @ApiOperation(value = "查询指定医院参数",notes = "查询指定医院参数")
    @ResponseBody
    @GetMapping(value = "/listparams")
    public JsonResponse listparams(){
        List<HospParamsBrief> result;

        try {
            if ((header != null) && (hospService != null))
                result = hospService.listparams(null,header.getHeader("hosp-ID"));
            else
                return JsonResponse.fail(1001, "查询指定医院参数失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取指定医院参数信息成功",result);
    }


    @ApiOperation(value = "获取指定ID的医院参数信息",notes = "获取指定ID的医院参数信息")
    @ResponseBody
    @GetMapping(value = "/getHospParamsbyid")
    public JsonResponse getHospParamsbyid(){

        if ((header != null) && (hospService != null))
        {
            try {
                HospParams hospParams = hospService.getHospParamsbyid(header.getHeader("hosp-ID"),null);
                return JsonResponse.success("获取指定ID的医院参数成功",hospParams);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的医院参数信息失败");
    }


    @ApiOperation(value = "删除指定ID的医院参数",notes = "删除指定ID的医院参数")
    @ResponseBody
    @GetMapping(value = "/deleteHospParams")
    public JsonResponse deleteHospParams(){
        boolean result;

        try {
            if ((header != null) && (hospService != null)) {
                result = hospService.deleteHospParams(header.getHeader("hosp-ID"), null);
            }
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的医院参数成功");
    }


    @ApiOperation(value = "保存指定医院参数，可以是新增或更新",notes = "保存指定医院信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/saveHospParams")
    public JsonResponse<HospParams> saveHospParams(@RequestBody @Valid HospParamsSaveRequest request)
    {
        HospParams hospParams = null;

        try {
            if ((header != null) && (hospService != null)) {
                hospParams = hospService.saveHospParams(header.getHeader("hosp-ID"), request);
            }

        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        if (hospParams != null)
        {
            return JsonResponse.success("保存医院参数成功", hospParams);
        }
        else
            return JsonResponse.fail(1001,"保存医院参数失败!");

    }
}
