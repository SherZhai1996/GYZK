package cn.edu.ujs.lp.intells.controller.basicinformation;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Grid;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridsubTreeRequest;
import cn.edu.ujs.lp.intells.common.entity.Hosp.GridBrief;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPDFRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.GridSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.GridService;
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


/**
 * 医院网格接口
 *
 * @author Meredith
 * @data 2019-10-04
 */
@Api(description = "医院网格接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Grid")
public class GridController {

    @Autowired
    HttpServletRequest header;

    @Autowired
    private GridService gridService;

    @Autowired
    private DBCommonService dbCommonService;

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

    @ApiOperation(value = "下载医院网格Excel模板",notes = "下载医院网格Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return gridService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"医院网格Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传医院网格Excel文件",notes = "上传医院网格Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            if (header != null)
                return gridService.ImportExcelData(header.getHeader("hosp-ID"), file);
            else
                return JsonResponse.fail(1009, "没有指定医院，医院网格Excel文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "医院网格Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出医院网格Excel数据",notes = "导出医院网格Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response){

        try {
            if (header != null)
                return gridService.exportExcelData(header.getHeader("hosp-ID"), response);
            else
                return JsonResponse.fail(1009, "没有指定医院，导出医院网格Excel数据失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "导出医院网格Excel数据失败:" + e.getMessage());
        }
    }


    @ApiOperation(value = "查询所有医院网格",notes = "查询所有医院网格")
    @ResponseBody
    @GetMapping(value = "/list")
    public JsonResponse<GridBrief> list(){
        List<GridBrief> result;

        try {
            if (header != null)
                result = dbCommonService.getGrids(header.getHeader("hosp-ID"),null,null,null);
            else
                return JsonResponse.fail(1001, "没有指定医院，获取所有医院网格信息失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, e.getMessage());
        }

        return JsonResponse.success("获取所有医院网格信息成功",result);
    }

    @ApiOperation(value = "获取指定ID的医院网格树形结构",notes = "获取指定ID的医院网格树形结构")
    @ResponseBody
    @GetMapping(value = "/getGridTree/{deptId}")
    public JsonResponse getGridTree(@PathVariable("deptId") String deptId){

        if ((header != null) && (dbCommonService != null))
        {
            try {

                List<Object> lst =null;

                if ((deptId != null)&&(deptId.trim()!=""))
                    lst = dbCommonService.getGridTreeStr(header.getHeader("hosp-ID"),deptId.trim(),null,null);
                else
                    lst = dbCommonService.getGridTreeStr(header.getHeader("hosp-ID"),null,null,null);

                return JsonResponse.success("获取指定ID的医院网格树形结构成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的医院网格树形结构失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"没有指定医院，获取指定ID的医院网格完整信息失败");
    }

    @ApiOperation(value = "获取指定ID的医院指定网格id的子网格列表",notes = "获取指定ID的医院指定网格id的子网格列表")
    @ResponseBody
    @GetMapping(value = "/getGridsonNode/{superGridId}")
    public JsonResponse getGridsonNode(@PathVariable("superGridId") String superGridId){

        if ((header != null) && (dbCommonService != null))
        {
            try {

                List<Object> lst =null;

                if ((superGridId != null) && (superGridId.trim() != ""))
                    lst = dbCommonService.getsubGridTreeStr(header.getHeader("hosp-ID"),superGridId.trim());
                else
                    lst = dbCommonService.getsubGridTreeStr(header.getHeader("hosp-ID"),null);

                return JsonResponse.success("获取指定ID的医院指定网格id的子网格列表成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的医院指定网格id的子网格列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"没有指定医院，获取指定ID的医院指定网格id的子网格列表失败");
    }

    @ApiOperation(value = "获取指定ID的医院指定网格id的子网格列表",notes = "获取指定ID的医院指定网格id的子网格列表")
    @ResponseBody
    @PostMapping(value = "/getsubGridTreeStrbyIds")
    public JsonResponse getsubGridTreeStrbyIds(@RequestBody @Valid GridsubTreeRequest request){

        if ((header != null) && (dbCommonService != null))
        {
            try {

                List<Object> lst =null;

                if ((request != null) && (request.getGridIdsList() != null) && (request.getGridIdsList().size()>0))
                    lst = dbCommonService.getsubGridTreeStrbyIds(header.getHeader("hosp-ID"),request.getGridIdsList());
                else
                    lst = dbCommonService.getsubGridTreeStr(header.getHeader("hosp-ID"),null);

                return JsonResponse.success("获取指定ID的医院指定网格id列表的子网格树形列表成功",lst);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的医院指定网格id列表的子网格树形列表失败:"+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"没有指定医院，获取指定ID的医院指定网格id列表的子网格树形列表失败");
    }

    @ApiOperation(value = "获取指定ID的医院网格完整信息",notes = "获取指定ID的医院网格完整信息")
    @ResponseBody
    @GetMapping(value = "/getById/{id}")
    public JsonResponse<Grid> getById(@PathVariable("id") String id)
    {

        if (gridService != null)
        {
            try {
                Grid ec = gridService.getbyId(id);
                return JsonResponse.success("获取指定ID的医院网格完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的医院网格完整信息失败："+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的医院网格完整信息失败");
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<GridBrief> page(@RequestBody @Valid GridPageRequest request)
    {

        if ((header != null) && (gridService != null)) {
            try {
                PageResponse<GridBrief> response = gridService.page(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("分页查询成功", response);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"医院网格分页查询失败："+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"医院网格分页查询失败");
    }

    @ApiOperation(value = "删除一个医院网格",notes = "删除一个医院网格")
    @ResponseBody
    @PostMapping(value = "/delete/{gridCode}")
    public JsonResponse delete(@PathVariable("gridCode") String gridCode)
    {

        if ((header != null) && (gridService != null)) {
            try {
                if ((gridCode == null) || (gridCode.length()>20)) JsonResponse.fail(1001,"指定的网格区域编码非法!");
                boolean result = gridService.delete(header.getHeader("hosp-ID"), gridCode);
                return JsonResponse.success("删除指定网格成功", result);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"删除指定网格["+gridCode+"]成功失败："+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"删除指定网格操作失败");
    }

    @ApiOperation(value = "保存一个医院网格信息，可以是新增或更新",notes = "保存一个医院网格信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<GridBrief> save(@RequestBody @Valid GridSaveRequest request)
    {
        Grid grid = null;

        //保存网格对象
        try {
            if ((header != null) && (gridService != null)) {
                grid = gridService.save(header.getHeader("hosp-ID"), request);
                return JsonResponse.success("保存医院网格信息成功", grid);
            }else
                return JsonResponse.fail(1001, "没有指定医院，医院网格保存失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1001, "保存医院网格信息操作失败："+e.getMessage());
        }
    }

    @ApiOperation(value = "上传医院网格图片文件",notes = "上传医院网格文件")
    @ResponseBody
    @PostMapping("/uploadGrid_picture/{gridId}")
    public JsonResponse uploadGrid_picture(@PathVariable("gridId") String gridId,@RequestParam("file") MultipartFile file)
    {
        try {
            if ((header != null) && (gridService != null))
                return gridService.uploadGrid_picture(header.getHeader("hosp-ID"), gridId,file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传医院网格图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传医院网格图片文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除医院网格图片文件",notes = "删除医院网格图片文件")
    @ResponseBody
    @PostMapping("/clearGrid_picture/{gridId}")
    public JsonResponse clearGrid_picture(@PathVariable("gridId") String gridId)
    {
        try {
            if ((header != null) && (gridService != null))
                return gridService.clearGrid_picture(gridId);
            else
                return JsonResponse.fail(1009, "没有指定医院，删除医院网格图片文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "删除医院网格图片失败:" + e.getMessage());
        }
    }

}
