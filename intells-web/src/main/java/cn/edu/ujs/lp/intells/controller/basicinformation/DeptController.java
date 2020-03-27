package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Department;
import cn.edu.ujs.lp.intells.common.entity.Hosp.DepartmentBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.DeptPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.DeptSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.DeptService;
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

@Api(description = "科室部门接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Dept")
public class DeptController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    private DeptService deptService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载科室部门Excel模板",notes = "下载科室部门Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return deptService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"科室部门Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传科室部门Excel文件",notes = "上传科室部门Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return deptService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，科室部门Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "科室部门Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出科室部门Excel数据",notes = "导出科室部门Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return deptService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出科室部门Excel数据失败:");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出科室部门Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有科室部门",notes = "查询所有科室部门")
    @ResponseBody
    @GetMapping(value = "/list")
    public JsonResponse list(){
        List<DepartmentBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getDepts(header.getHeader("hosp-ID"),null);
            else
                return JsonResponse.fail(1001, "没有指定医院，查询所有科室部门失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "查询所有科室部门失败"+e.getMessage());
        }

        return JsonResponse.success("获取所有科室部门信息成功",result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<DepartmentBrief> page(@RequestBody @Valid DeptPageRequest request)
    {
        try {
            if (header != null) {
                PageResponse response = deptService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("科室部门分页查询成功", response);
            } else
                return JsonResponse.fail(1001, "没有指定医院，科室部门分页查询失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "科室部门分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "获取指定医院ID的科室部门树形结构",notes = "获取指定医院ID的科室部门树形结构")
    @ResponseBody
    @GetMapping(value = "/getDepartmentTreeStr")
    public JsonResponse getExteamTreeStr(){

        if ((header != null) && (dbCommonService != null))
        {
            try {
                List<Object> lst = dbCommonService.getDeptTreeStr(header.getHeader("hosp-ID"),null);

                return JsonResponse.success("获取指定医院ID的科室部门树形结构成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定医院ID的科室部门树形结构失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定医院ID的科室部门树形结构失败");
    }

    @ApiOperation(value = "获取指定ID的科室部门完整信息",notes = "获取指定ID的科室部门完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){

        if (deptService != null)
        {
            try {
                Department ec = deptService.getByID(id);
                return JsonResponse.success("获取指定ID的科室部门完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的科室部门完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的科室部门",notes = "删除指定ID的科室部门")
    @ResponseBody
    @GetMapping(value = "/delete/{DCode}")
    public JsonResponse delete(@PathVariable("DCode") String DCode){
        boolean result;

        try {
            if ((DCode == null) || (DCode.length()>20)) return JsonResponse.fail(1001,"科室编码非法!");

            result = deptService.delete(DCode);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1001,"删除科室部门失败："+e.getMessage());
        }

        return JsonResponse.success("删除指定ID的科室部门信息成功",DCode);
    }

    @ApiOperation(value = "保存一个科室部门信息，可以是新增或更新",notes = "保存一个科室部门信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<DepartmentBrief> save(@RequestBody @Valid DeptSaveRequest request)
    {
        Department department = null;

        try {
            if (header != null) {
                department = deptService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存科室部门信息成功", department);
            }
            else
                return JsonResponse.fail(1001, "没有指定医院，科室部门信息保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

    }

}
