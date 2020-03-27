package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exteam;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExteamPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.Excompany.ExteamSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.ExteamService;
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

@Api(description = "服务班组接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Exteam")
public class ExteamController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    private ExteamService exteamService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载服务班组Excel模板",notes = "下载服务班组Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return exteamService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"服务班组Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传服务班组Excel文件",notes = "上传服务班组Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return exteamService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，服务班组Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "服务班组Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出服务班组Excel数据",notes = "导出服务班组Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return exteamService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出服务班组Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出服务班组Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有服务班组",notes = "查询所有服务班组")
    @ResponseBody
    @GetMapping(value = "/list/{excompanyID}/{exservicesId}")
    public JsonResponse list(@PathVariable("excompanyID") String excompanyID,@PathVariable("exservicesId") String exservicesId){
        List<ExteamBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getExteams(header.getHeader("hosp-ID"), excompanyID.trim(), exservicesId.trim());
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有服务班组失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有服务班组信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<ExteamBrief> page(@RequestBody @Valid ExteamPageRequest request)
    {
        try {
            if (header != null) {
                PageResponse response = exteamService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的服务班组树形结构",notes = "获取指定ID的服务班组树形结构")
    @ResponseBody
    @GetMapping(value = "/getExteamTreeStr/{excompanyID}/{exservicesId}")
    public JsonResponse getExteamTreeStr(@PathVariable("excompanyID") String excompanyID,@PathVariable("exservicesId") String exservicesId){

        if ((header != null) && (dbCommonService != null))
        {
            try {
                List<Object> lst = dbCommonService.getExteamTreeStr(header.getHeader("hosp-ID"),excompanyID,exservicesId);

                return JsonResponse.success("获取指定ID的服务班组树形结构成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的服务班组树形结构失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的服务班组树形结构失败");
    }

    @ApiOperation(value = "获取指定ID的服务班组完整信息",notes = "获取指定ID的服务班组完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (exteamService != null)
        {
            try {
                Exteam ec = exteamService.getByID(id);
                return JsonResponse.success("获取指定ID的服务班组完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的服务班组完整信息失败");
    }


    @ApiOperation(value = "删除指定编码的服务班组",notes = "删除指定编码的服务班组")
    @ResponseBody
    @PostMapping(value = "/delete/{tCode}")
    public JsonResponse delete(@PathVariable("tCode") String tCode){
        boolean result;

        try {
            result = exteamService.delete(tCode);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,e.getMessage());
        }

        return JsonResponse.success("删除指定ID的服务班组信息成功",tCode);
    }

    @ApiOperation(value = "保存一个服务班组信息，可以是新增或更新",notes = "保存一个服务班组信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<ExteamBrief> save(@RequestBody @Valid ExteamSaveRequest request)
    {
        Exteam exteam = null;

        try {
            if (header != null) {
                exteam = exteamService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存服务班组信息成功", exteam);
            } else
                return JsonResponse.fail(1001, "没有指定医院，保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }
    }
}
