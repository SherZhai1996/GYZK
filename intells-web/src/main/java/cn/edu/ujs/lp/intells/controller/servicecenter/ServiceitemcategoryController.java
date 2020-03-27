package cn.edu.ujs.lp.intells.controller.servicecenter;

import cn.edu.ujs.lp.intells.common.request.IdRequest;
import cn.edu.ujs.lp.intells.servicecenter.common.util.JsonResponse1;
import cn.edu.ujs.lp.intells.servicecenter.request.HospIdRequest;
import cn.edu.ujs.lp.intells.servicecenter.request.Serviceitemcategory.*;
import cn.edu.ujs.lp.intells.servicecenter.service.ServiceitemcategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Api(description = "服务事项接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/servicematter")
public class ServiceitemcategoryController {
    @Autowired
    private ServiceitemcategoryService serviceitemcategoryService;

    @ApiOperation(value = "通过一级找二级",notes = "通过一级找二级")
    @PostMapping(value = "/oneToTwo")
    public JsonResponse1 oneToTwo(@RequestBody @Valid IdRequest request) {
        return serviceitemcategoryService.oneToTwo(request);
    }

    @ApiOperation(value = "通过二级找三级",notes = "通过二级找三级")
    @PostMapping(value = "/twoToThree")
    public JsonResponse1 twoToThree(@RequestBody @Valid IdRequest request) {
        return serviceitemcategoryService.oneToTwo(request);
    }

    @ApiOperation(value = "查询Json",notes = "查询Json")
    @PostMapping(value = "/getJson")
    public JsonResponse1 getJson() {
        return JsonResponse1.success(serviceitemcategoryService.getJson());
    }

    @ApiOperation(value = "查询三级json",notes = "查询三级json")
    @PostMapping(value = "/getJson1")
    public JsonResponse1 getJson1() throws Exception{
        return JsonResponse1.success(serviceitemcategoryService.getJson1());
    }
    @ApiOperation(value = "初始化服务事项",notes = "初始化服务事项")
    @PostMapping(value = "/init")
    public JsonResponse1 init(HospIdRequest request) {
        return serviceitemcategoryService.init(request);
    }

    @ApiOperation(value = "更新服务事项",notes = "更新服务事项")
    @PostMapping(value = "/update")
    public JsonResponse1 update(@RequestBody @Valid ServiceitemcategoryUpdateRequest request) {
        serviceitemcategoryService.update(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "创建一级服务事项",notes = "创建一级服务事项")
    @PostMapping(value = "/create")
    public JsonResponse1 create(@RequestBody @Valid ServiceitemcategoryCreateRequest request) {
        serviceitemcategoryService.create(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除一级服务事项",notes = "删除一级服务事项")
    @PostMapping(value = "/delete")
    public JsonResponse1 delete(@RequestBody @Valid IdRequest request) {
        serviceitemcategoryService.delete(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "分页查询一级服务事项",notes = "分页查询一级服务事项")
    @PostMapping(value = "/queryByPage")
    public JsonResponse1 queryByPage(@RequestBody @Valid ServiceitemcategoryQueryRequest request) {
        return serviceitemcategoryService.queryByPage(request);
    }

    @ApiOperation(value = "创建一条二级分类",notes = "创建一条二级分类")
    @PostMapping(value = "/create2")
    public JsonResponse1 create2(@RequestBody @Valid ServiceitemcategoryCreate1Request request) {
        serviceitemcategoryService.create2(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "删除一条二级分类",notes = "删除一条二级分类")
    @PostMapping(value = "/delete2")
    public JsonResponse1 delete2(@RequestBody @Valid IdRequest request) {
        serviceitemcategoryService.delete2(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value = "分页查询二级服务事项",notes = "分页查询二级服务事项")
    @PostMapping(value = "/queryByPage2")
    public JsonResponse1 queryByPage2(@RequestBody @Valid ServiceitemcategoryQuery1Request request) {
        return serviceitemcategoryService.queryByPage2(request);
    }

    @ApiOperation(value="更新二级服务事项",notes = "更新二级服务事项")
    @PostMapping(value = "/update2")
    public JsonResponse1 update2(@RequestBody @Valid ServiceitemcategoryUpdate1Request request){
        serviceitemcategoryService.update2(request);
        return JsonResponse1.success();
    }

    @ApiOperation(value="服务事项导出",notes = "服务事项导出")
    @PostMapping(value = "/exportExcelData")
    @ResponseBody
    public JsonResponse1 exportExcelData(HttpServletResponse response)throws Exception{
        return serviceitemcategoryService.exportExcelData(response);
    }

    @ApiOperation(value="服务事项导入",notes = "服务事项导入")
    @PostMapping(value = "/importExcelData")
    public JsonResponse1 ImportExcelData(MultipartFile file)throws Exception{
        return serviceitemcategoryService.importExcelData(file);
    }

//    @ApiOperation(value = "创建一条三级分类",notes = "创建一条三级分类")
//    @PostMapping(value = "/create3")
//    public JsonResponse1 create3(@RequestBody @Valid ServiceitemcategoryCreate1Request request) {
//        serviceitemcategoryService.create2(request);
//        return JsonResponse1.success();
//    }
//
//    @ApiOperation(value = "删除一条三级分类",notes = "删除一条三级分类")
//    @PostMapping(value = "/delete3")
//    public JsonResponse1 delete3(@RequestBody @Valid IdRequest request) {
//        serviceitemcategoryService.delete2(request);
//        return JsonResponse1.success();
//    }
//
//    @ApiOperation(value = "分页查询三级服务事项",notes = "分页查询三级服务事项")
//    @PostMapping(value = "/queryByPage3")
//    public JsonResponse1 queryByPage3(@RequestBody @Valid ServiceitemcategoryQuery1Request request) {
//        return serviceitemcategoryService.queryByPage2(request);
//    }
//
//    @ApiOperation(value="更新三级服务事项",notes = "更新三级服务事项")
//    @PostMapping(value = "/update3")
//    public JsonResponse1 update3(@RequestBody @Valid ServiceitemcategoryUpdate1Request request){
//        serviceitemcategoryService.update2(request);
//        return JsonResponse1.success();
//    }
}
