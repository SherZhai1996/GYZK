package cn.edu.ujs.lp.intells.controller.Common;

import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.GridService;
import cn.edu.ujs.lp.intells.common.entity.User.RoleBrief;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.request.GetGridsRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
//import cn.edu.ujs.lp.intells.systemmanagement.entity.HospFunctionBrief;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.UserService;
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

@Api(description = "数据对象的公共接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/DBCommon")
public class DBCommonController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private UserService userService;

    @Autowired
    private GridService gridService;

    @Autowired
    HttpServletRequest header;

    @ApiOperation(value = "获取医院的地图显示logo文件相对路径名",notes = "获取医院的地图显示logo文件相对路径名")
    @ResponseBody
    @GetMapping(value = "/getHosplogoiconfile")
    public JsonResponse getHosplogoiconfile(){
        if ( dbCommonService != null) {
            try {
                return JsonResponse.success("获取医院的地图显示logo文件相对路径名成功",dbCommonService.getHosplogoiconfile());
            } catch (Exception e) {
                return JsonResponse.fail(1209, "获取医院的地图显示logo文件相对路径名失败:" + e.getMessage());
            }
        }
        else return JsonResponse.fail(1109,"获取医院的地图显示logo文件相对路径名失败！");
    }

    @ApiOperation(value = "获取指定地点经纬度",notes = "获取指定地点经纬度")
    @ResponseBody
    @GetMapping(value = "/getCoordinate/{addr}")
    public JsonResponse getCoordinate(@PathVariable("addr") String addr){
        if ( dataDictionaryService != null) {
            try {
                return JsonResponse.success("获取指定地点经纬度成功",dataDictionaryService.getCoordinate(addr));
            } catch (Exception e) {
                return JsonResponse.fail(1209, "获取指定地点经纬度失败:" + e.getMessage());
            }
        }
        else return JsonResponse.fail(1109,"获取指定地点经纬度失败！");
    }


    @ApiOperation(value = "输出选中网格的二维码标签",notes = "输出选中网格的二维码标签")
    @ResponseBody
    @PostMapping(value = "/gridQrCode")
    public JsonResponse gridQrCode(@RequestBody @Valid GridPDFRequest gridPDFRequest, HttpServletResponse response){
        if ((header != null) && (gridService != null)) {
            try {
                return gridService.ExportGridPdf(header.getHeader("hosp-ID"), gridPDFRequest, response);
            } catch (Exception e) {
                return JsonResponse.fail(1109, "输出网格区域二维码标签失败:" + e.getMessage());
            }
        }
        else return JsonResponse.fail(1109,"输出网格区域二维码标签失败！");
    }


    @ApiOperation(value = "依据网格区域ID获取蓝牙信标UUID",notes = "依据网格区域ID获取蓝牙信标UUID")
    @ResponseBody
    @GetMapping(value = "/getIbeaconUUIDbygridId/{gridId}")
    public JsonResponse getIbeaconUUIDbygridId(@PathVariable("gridId") String gridId) {
        String result = null;

        if (dbCommonService != null) {
            try {
                result = dbCommonService.getIbeaconUUIDbygridId(gridId);
                return JsonResponse.success("依据网格区域ID获取蓝牙信标UUID成功", result);
            } catch (Exception e) {
                return JsonResponse.fail(1001, "依据网格区域ID获取蓝牙信标UUID失败:" + e.getMessage());
            }
        } else
            return JsonResponse.fail(1001, "公共接口访问失败!");
    }


    @ApiOperation(value = "上传系统用户logo图片文件",notes = "上传系统用户logo图片文件")
    @ResponseBody
    @PostMapping("/upload_logopicture/{userId}")
    public JsonResponse upload_logopicture(@PathVariable("userId") String userId,@RequestParam("file") MultipartFile file) {
        try {
            if (userService != null)
                return userService.upload_logopicture(userId, file);
            else
                return JsonResponse.fail(1009, "服务启动失败，上传系统用户logo图片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "上传系统用户logo图片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除系统用户logo图片文件",notes = "删除系统用户logo图片文件")
    @ResponseBody
    @PostMapping("/clear_logopicture/{userId}")
    public JsonResponse clear_logopicture(@PathVariable("userId") String userId) {
        try {
            if (userService != null)
                return userService.clear_logopicture(userId);
            else
                return JsonResponse.fail(1009, "服务启动失败，删除系统用户logo图片文件失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "删除系统用户logo图片失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "获取系统用户角色列表列表",notes = "获取系统用户角色列表列表")
    @ResponseBody
    @GetMapping(value = "/getSystemRole")
    public JsonResponse getSystemRole(){
        List<RoleBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.getSystemRole();
                return JsonResponse.success("获取系统用户角色列表列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取系统用户角色列表列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "为前端选择获取系统用户角色列表列表",notes = "为前端选择获取系统用户角色列表列表")
    @ResponseBody
    @GetMapping(value = "/getSystemRoleForSelect")
    public JsonResponse getSystemRoleForSelect(){
        List<RoleBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.getSystemRoleForSelect();
                return JsonResponse.success("获取系统用户角色列表列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取系统用户角色列表列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "获取医院职工角色列表列表",notes = "获取医院职工角色列表列表")
    @ResponseBody
    @GetMapping(value = "/getStaffRole")
    public JsonResponse getStaffRole(){
        List<RoleBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.getStaffRole();
                return JsonResponse.success("获取医院职工角色列表列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取医院职工角色列表列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "获取外委职工角色列表列表",notes = "获取外委职工角色列表列表")
    @ResponseBody
    @GetMapping(value = "/getExstaffRole")
    public JsonResponse getExstaffRole(){
        List<RoleBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.getExstaffRole();
                return JsonResponse.success("获取外委职工角色列表列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取外委职工角色列表列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }



    @ApiOperation(value = "依据医院名称获取医院列表，支持模糊查询和null",notes = "依据医院名称获取医院列表，支持模糊查询和null")
    @ResponseBody
    @GetMapping(value = "/getHosps/{hospName}")
    public JsonResponse getHosps(@PathVariable("hospName") String hospName){
        List<HospBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.getHosps(hospName);
                return JsonResponse.success("获取医院列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取医院列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "获取医院功能列表",notes = "获取医院功能列表")
    @ResponseBody
    @GetMapping(value = "/gethospFunctions")
    public JsonResponse gethospFunctions(){
        List<HospFunctionBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                lst = dbCommonService.gethospFunctions();
                return JsonResponse.success("获取医院功能列表成功", lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取医院功能列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和科室名称获取科室列表，科室名称支持模糊查询和null",notes = "依据医院ID和科室名称获取科室列表，科室名称支持模糊查询和null")
    @ResponseBody
    @GetMapping(value = "/getDepts/{deptName}")
    public JsonResponse getDepts(@PathVariable("deptName") String deptName){
        List<DepartmentBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getDepts(header.getHeader("hosp-ID"),deptName);
                    return JsonResponse.success("获取科室列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取科室列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和科室名称获取科室树形列表，科室名称支持模糊查询和null",notes = "依据医院ID和科室名称获取科室树形列表，科室名称支持模糊查询和null")
    @ResponseBody
    @GetMapping(value = "/getDeptTreeStr/{deptName}")
    public JsonResponse getDeptTreeStr(@PathVariable("deptName") String deptName){
        List<Object> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getDeptTreeStr(header.getHeader("hosp-ID"),deptName);
                    return JsonResponse.success("获取科室树形列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取科室树形列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和科室ID、父节点ID和网格名称获取网格区域列表，网格区域名称支持模糊查询和null",notes = "依据医院ID和科室ID、父节点ID和网格名称获取网格区域列表，网格区域名称支持模糊查询和null")
    @ResponseBody
    @PostMapping(value = "/getGrids")
    public JsonResponse getGrids(@RequestBody @Valid GetGridsRequest request){
        List<GridBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getGrids(header.getHeader("hosp-ID"),request.getDeptId(),request.getSuperGridId(),request.getGridName());
                    return JsonResponse.success("获取网格区域列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "依据医院ID和父节点ID获取下一级网格区域列表",notes = "依据医院ID和父节点ID获取下一级网格区域列表")
    @ResponseBody
    @GetMapping(value = "/getsubGrids/{superGridId}")
    public JsonResponse getsubGrids(@PathVariable("superGridId") String superGridId){
        List<GridBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getsubGrids(header.getHeader("hosp-ID"),superGridId);
                    return JsonResponse.success("获取网格区域列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "依据医院ID和父节点ID获取所有子网格区域列表",notes = "依据医院ID和父节点ID获取所有网格区域列表")
    @ResponseBody
    @GetMapping(value = "/getallsubGrids/{superGridId}")
    public JsonResponse getallsubGrids(@PathVariable("superGridId") String superGridId){
        List<GridBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getallsubGrids(header.getHeader("hosp-ID"),superGridId);
                    return JsonResponse.success("获取网格区域列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "依据医院ID获取所有楼栋对象列表",notes = "依据医院ID获取所有楼栋对象列表")
    @ResponseBody
    @GetMapping(value = "/getBuilds")
    public JsonResponse getBuilds(){
        List<GridBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getBuilds(header.getHeader("hosp-ID"));
                    return JsonResponse.success("获取网格区域列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "依据网格功能类别获取网格列表，网格区域名称支持模糊查询和null",notes = "依据网格功能类别获取网格列表，网格区域名称支持模糊查询和null")
    @ResponseBody
    @PostMapping(value = "/getGridsbyPlaceclass/{gridPlaceclassid}/{gridName}")
    public JsonResponse getGridsbyPlaceclass(@PathVariable("gridPlaceclassid") String gridPlaceclassid,@PathVariable("gridName") String gridName){
        List<GridBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getGridsbyPlaceclass(header.getHeader("hosp-ID"),gridPlaceclassid,gridName);
                    return JsonResponse.success("获取网格区域列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }



    @ApiOperation(value = "依据医院ID和科室ID、父节点ID和网格名称获取网格区域树形列表，网格区域名称支持模糊查询和null",notes = "依据医院ID和科室ID、父节点ID和网格名称获取网格区域树形列表，网格区域名称支持模糊查询和null")
    @ResponseBody
    @PostMapping(value = "/getGridTreeStr")
    public JsonResponse getGridTreeStr(@RequestBody @Valid GetGridsRequest request){
        List<Object> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getGridTreeStr(header.getHeader("hosp-ID"),request.getDeptId(),request.getSuperGridId(),request.getGridName());
                    return JsonResponse.success("获取网格区域树形列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域树形列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和父节点ID获取网格区域树形列表",notes = "依据医院ID和父节点ID获取网格区域树形列表")
    @ResponseBody
    @GetMapping(value = "/getsubGridTreeStr/{superGridId}")
    public JsonResponse getsubGridTreeStr(@PathVariable("superGridId") String superGridId){
        List<Object> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getsubGridTreeStr(header.getHeader("hosp-ID"),superGridId);
                    return JsonResponse.success("获取网格区域树形列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取网格区域树形列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和科室ID和在职员工名称获取在职员工列表，在职员工名称支持模糊查询和null",notes = "依据医院ID和科室ID和在职员工名称获取在职员工列表，在职员工名称支持模糊查询和null")
    @ResponseBody
    @GetMapping(value = "/getStaffs/{deptId}/{staffName}")
    public JsonResponse getStaffs(@PathVariable("deptId") String deptId,@PathVariable("staffName") String staffName){
        List<StaffBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getStaffs(header.getHeader("hosp-ID"),deptId,staffName);
                    return JsonResponse.success("获取在职员工列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取在职员工列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID获取外委公司列表",notes = "依据医院ID获取外委公司列表")
    @ResponseBody
    @GetMapping(value = "/getExcompanys")
    public JsonResponse getExcompanys(){
        List<ExcompanyBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getExcompanys(header.getHeader("hosp-ID"));
                    return JsonResponse.success("获取外委公司列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取外委公司列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和外委公司ID获取业务部门列表",notes = "依据医院ID和外委公司ID获取业务部门列表")
    @ResponseBody
    @GetMapping(value = "/getExservices/{excompanyID}")
    public JsonResponse getExservices(@PathVariable("excompanyID") String excompanyID){
        List<ExServicesBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getExservices(header.getHeader("hosp-ID"),excompanyID);
                    return JsonResponse.success("获取业务部门列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取业务部门列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和外委公司ID、业务部门ID获取服务班组列表",notes = "依据医院ID和外委公司ID、业务部门ID获取服务班组列表")
    @ResponseBody
    @GetMapping(value = "/getExteams/{excompanyID}/{exservicesId}")
    public JsonResponse getExteams(@PathVariable("excompanyID") String excompanyID,@PathVariable("exservicesId") String exservicesId){
        List<ExteamBrief> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getExteams(header.getHeader("hosp-ID"),excompanyID,exservicesId);
                    return JsonResponse.success("获取服务班组列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取服务班组列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和外委公司ID、业务部门ID获取服务班组树形列表",notes = "依据医院ID和外委公司ID、业务部门ID获取服务班组树形列表")
    @ResponseBody
    @GetMapping(value = "/getExteamTreeStr/{excompanyID}/{exservicesId}")
    public JsonResponse getExteamTreeStr(@PathVariable("excompanyID") String excompanyID,@PathVariable("exservicesId") String exservicesId){
        List<Object> lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getExteamTreeStr(header.getHeader("hosp-ID"),excompanyID,exservicesId);
                    return JsonResponse.success("获取服务班组树形列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取服务班组树形列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据医院ID和外委公司ID、服务班组ID获取外委职工列表",notes = "依据医院ID和外委公司ID、服务班组ID获取外委职工列表")
    @ResponseBody
    @GetMapping(value = "/getExstaffs/{excompanyID}/{exteamId}")
    public JsonResponse getExstaffs(@PathVariable("excompanyID") String excompanyID,@PathVariable("exteamId") String exteamId){
        List<ExstaffBrief>  lst = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    lst = dbCommonService.getExstaffs(header.getHeader("hosp-ID"),excompanyID,exteamId);
                    return JsonResponse.success("获取外委职工列表成功", lst);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取外委职工列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "依据医院ID获取医院编码",notes = "依据医院ID获取医院编码")
    @ResponseBody
    @GetMapping(value = "/getHospCodebyId")
    public JsonResponse getHospCodebyId(){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getHospCodebyId(header.getHeader("hosp-ID"));
                    return JsonResponse.success("依据医院ID获取医院编码成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"依据医院ID获取医院编码失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }

    @ApiOperation(value = "由科室ID获取科室名称",notes = "由科室ID获取科室名称")
    @ResponseBody
    @GetMapping(value = "/getDeptFullnamebyId/{deptId}")
    public JsonResponse getDeptFullnamebyId(@PathVariable("deptId") String deptId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getDeptFullnamebyId(deptId);
                    return JsonResponse.success("由科室ID获取科室名称成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由科室ID获取科室名称失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由科室全名获取科室ID",notes = "由科室全名获取科室ID")
    @ResponseBody
    @GetMapping(value = "/getDeptIdbyFullname/{deptFullname}")
    public JsonResponse getDeptIdbyFullname(@PathVariable("deptFullname") String deptFullname){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getDeptIdbyFullname(header.getHeader("hosp-ID"),deptFullname);
                    return JsonResponse.success("由科室全名获取科室ID成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由科室全名获取科室ID失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由网格区域ID获取网格全名称",notes = "由网格区域ID获取网格全名称")
    @ResponseBody
    @GetMapping(value = "/getGridFullnamebyId/{gridId}")
    public JsonResponse getGridFullnamebyId(@PathVariable("gridId") String gridId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getGridFullnamebyId(gridId);
                    return JsonResponse.success("由网格区域ID获取网格全名称成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由网格区域ID获取网格全名称失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由网格区域全名获取网格ID",notes = "由网格区域全名获取网格ID")
    @ResponseBody
    @GetMapping(value = "/getGridIdbyFullname/{gridFullname}")
    public JsonResponse getGridIdbyFullname(@PathVariable("gridFullname") String gridFullname){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getGridIdbyFullname(header.getHeader("hosp-ID"),gridFullname);
                    return JsonResponse.success("由网格区域全名获取网格ID成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由网格区域全名获取网格ID失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由在职员工ID获取在职员工全名称",notes = "由在职员工ID获取在职员工全名称")
    @ResponseBody
    @GetMapping(value = "/getStaffFullnamebyId/{staffId}")
    public JsonResponse getStaffFullnamebyId(@PathVariable("staffId") String staffId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getStaffFullnamebyId(staffId);
                    return JsonResponse.success("由在职员工ID获取在职员工全名称成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由在职员工ID获取在职员工全名称失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由服务班组ID获取服务班组全名称",notes = "由服务班组ID获取服务班组全名称")
    @ResponseBody
    @GetMapping(value = "/getExteamFullnamebyId/{exteamId}")
    public JsonResponse getExteamFullnamebyId(@PathVariable("exteamId") String exteamId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getExteamFullnamebyId(exteamId);
                    return JsonResponse.success("由服务班组ID获取服务班组全名称成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由服务班组ID获取服务班组全名称失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "依据服务班组ID获取包含服务公司和服务部门的服务班组全名",notes = "依据服务班组ID获取包含服务公司和服务部门的服务班组全名")
    @ResponseBody
    @GetMapping(value = "/getExteamFullpathnamebyId/{exteamId}")
    public JsonResponse getExteamFullpathnamebyId(@PathVariable("exteamId") String exteamId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getExteamFullpathnamebyId(exteamId);
                    return JsonResponse.success("依据服务班组ID获取包含服务公司和服务部门的服务班组全名成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"依据服务班组ID获取包含服务公司和服务部门的服务班组全名失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }


    @ApiOperation(value = "由职工ID获取外委职工全名:外委公司名称+服务班组名称+职工姓名",notes = "由职工ID获取外委职工全名:外委公司名称+服务班组名称+职工姓名")
    @ResponseBody
    @GetMapping(value = "/getExstaffFullnamebyId/{exstaffId}")
    public JsonResponse getExstaffFullnamebyId(@PathVariable("exstaffId") String exstaffId){
        String result = null;

        if (dbCommonService != null)
        {
            try {
                if (header != null) {
                    result = dbCommonService.getExstaffFullnamebyId(exstaffId);
                    return JsonResponse.success("由职工ID获取外委职工全名:外委公司名称+服务班组名称+职工姓名成功", result);
                }
                else
                    return JsonResponse.fail(1001,"没指定医院");
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"由职工ID获取外委职工全名:外委公司名称+服务班组名称+职工姓名失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"公共接口访问失败!");
    }
}
