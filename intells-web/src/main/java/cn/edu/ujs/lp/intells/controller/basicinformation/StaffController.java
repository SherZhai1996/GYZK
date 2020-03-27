package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Staff;
import cn.edu.ujs.lp.intells.common.entity.Hosp.StaffBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.StaffService;
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

@Api(description = "在职职工接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Staff")
public class StaffController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载在职职工Excel模板",notes = "下载在职职工Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return staffService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"在职职工Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传在职职工Excel文件",notes = "上传在职职工Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return staffService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，在职职工Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "在职职工Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出在职职工Excel数据",notes = "导出在职职工Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return staffService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出在职职工Excel数据失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出在职职工Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有在职职工",notes = "查询所有在职职工")
    @ResponseBody
    @GetMapping(value = "/list/{deptId}")
    public JsonResponse list(
            @ApiParam(value = "科室部门ID", required = false)
            @PathVariable("deptId") String deptId) {
        List<StaffBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getStaffs(header.getHeader("hosp-ID"), deptId.trim(),null);
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有在职职工失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "查询所有在职职工失败:"+e.getMessage());
        }

        return JsonResponse.success("获取所有在职职工信息成功", result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<StaffBrief> page(@RequestBody @Valid StaffPageRequest request) {
        try {
            if (header != null) {
                PageResponse response = staffService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("在职职工分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，在职职工分页查询失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "在职职工分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定ID的在职职工完整信息",notes = "获取指定ID的在职职工完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){
        if (staffService != null)
        {
            try {
                Staff ec = staffService.getByID(id);
                return JsonResponse.success("获取指定ID的在职职工完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的在职职工完整信息失败："+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的在职职工完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的在职职工",notes = "删除指定ID的在职职工")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id) {
        boolean result;

        try {
            result = staffService.delete(id);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "删除指定ID的在职职工信息失败:"+e.getMessage());
        }

        return JsonResponse.success("删除指定ID的在职职工信息成功", id);
    }


    @ApiOperation(value = "保存一个在职职工信息，可以是新增或更新",notes = "保存一个在职职工信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<StaffBrief> save(@RequestBody @Valid StaffSaveRequest request) {
        Staff staff = null;

        try {
            if (header != null) {
                staff = staffService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存在职职工信息成功", staff);
            } else
                return JsonResponse.fail(1001, "没有指定医院，在职职工保存失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "在职职工保存失败:"+e.getMessage());
        }
    }

    @ApiOperation(value = "上传在职职工照片文件",notes = "上传在职职工照片文件")
    @ResponseBody
    @PostMapping("/uploadStaff_picture/{staffId}")
    public JsonResponse uploadStaff_picture(@PathVariable("staffId") String staffId,@RequestParam("file") MultipartFile file) {
        try {
            if ((header != null) && (staffService != null))
                return staffService.uploadStaff_picture(header.getHeader("hosp-ID"), staffId, file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传医院在职职工照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "上传在职职工照片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除在职职工照片文件",notes = "删除在职职工照片文件")
    @ResponseBody
    @PostMapping("/clearStaff_picture/{staffId}")
    public JsonResponse clearStaff_picture(@PathVariable("staffId") String staffId) {
        try {
            if ((header != null) && (staffService != null))
                return staffService.clearStaff_picture(staffId);
            else
                return JsonResponse.fail(1009, "没有指定医院，删除在职职工照片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "删除在职职工照片失败:" + e.getMessage());
        }
    }
}
