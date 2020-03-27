package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialcategory.MaterialcategoryCreate1Request;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialcategory.MaterialcategoryCreateRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialcategory.MaterialcategoryQueryRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Materialcategory.MaterialcategoryUpdateRequest;
import cn.edu.ujs.lp.intells.servicecenter.service.MaterialcategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Api(description = "耗材接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/material")
public class MaterialcategoryController {
    @Autowired
    private MaterialcategoryService materialcategoryService;

    @ApiOperation(value = "查询Json",notes = "查询Json")
    @PostMapping(value = "/getJson")
    public JsonResponse1 getJson(HospIdRequest request) {
        return JsonResponse1.success(materialcategoryService.getJson(request));
    }

    @ApiOperation(value = "初始化耗材",notes = "初始化耗材")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return materialcategoryService.init(request);
    }

    @ApiOperation(value = "通过一级找二级",notes = "通过一级找二级")
    @PostMapping(value = "/oneToTwo")
    public JsonResponse1 oneToTwo(@RequestBody @Valid IdRequest request) {
        return materialcategoryService.oneToTwo(request);
    }

    @ApiOperation(value = "创建一级耗材",notes = "创建一级耗材")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid MaterialcategoryCreateRequest request) {
        materialcategoryService.create(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除一级耗材",notes = "删除一级耗材")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        materialcategoryService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "更新耗材",notes = "更新耗材")
    @PostMapping(value = "/update")
    public JsonResponse1 update(@RequestBody @Valid MaterialcategoryUpdateRequest request) {
        materialcategoryService.update(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "分页查询一级耗材",notes = "分页查询一级耗材")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid MaterialcategoryQueryRequest request) {
        return materialcategoryService.queryByPage(request);
    }

    @ApiOperation(value = "创建一条二级耗材",notes = "创建一条二级耗材")
    @PostMapping(value = "/create2")
    public JsonResponse1 create2(@RequestBody @Valid MaterialcategoryCreate1Request request) {
        materialcategoryService.create2(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除一条二级耗材",notes = "删除一条二级耗材")
    @PostMapping(value = "/delete2")
    public JsonResponse1 delete2(@RequestBody @Valid IdRequest request) {
        materialcategoryService.delete2(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "分页查询二级耗材",notes = "分页查询二级耗材")
    @PostMapping(value = "/queryByPage2")
    public JsonResponse1 queryByPage2(@RequestBody @Valid MaterialcategoryQueryRequest request) {
        return materialcategoryService.queryByPage2(request);
    }

    @ApiOperation(value = "分页查询二级耗材",notes = "分页查询二级耗材")
    @PostMapping(value = "/update2")
    public JsonResponse1 update2(@RequestBody @Valid MaterialcategoryUpdateRequest request) {
         materialcategoryService.update2(request);
         return JsonResponse1.success();
    }

    @ApiOperation(value = "三级树形结构",notes = "三级树形结构")
    @PostMapping(value = "/getJson1")
    public JsonResponse1 getJson1(@RequestBody @Valid HospIdRequest request) throws Exception{
        return JsonResponse1.success(materialcategoryService.getJson1(request));
    }


    @ApiOperation(value = "导出数据",notes = "导出数据")
    @PostMapping(value = "/exportExcelData")
    @ResponseBody
    public JsonResponse1 exportExcelData(HttpServletResponse response) throws Exception{
        return JsonResponse1.success(materialcategoryService.exportExcelData(response));
    }


    @ApiOperation(value = "导入数据",notes = "导入数据")
    @PostMapping(value = "/importExcelData")
    public JsonResponse1 ImportExcelData(MultipartFile file) throws Exception{
        return materialcategoryService.importExcelData(file);
    }
}
