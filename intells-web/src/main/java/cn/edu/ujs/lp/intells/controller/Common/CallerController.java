package cn.edu.ujs.lp.intells.controller.Common;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.CallerService;
import cn.edu.ujs.lp.intells.common.service.FileService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api(description = "服务中心公共接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/Caller")
public class CallerController {
    @Autowired
    private CallerService callerService;

    @Autowired
    HttpServletRequest header;

    @ApiOperation(value = "依据网格区域电话号码获取值班人员电话",notes = "依据网格区域电话号码获取值班人员电话")
    @ResponseBody
    @GetMapping(value = "/getOndutyExStaffTelBygridTel/{gridTel}")
    public JsonResponse getOndutyExStaffTelBygridTel(@PathVariable("gridTel") String gridTel){
        //ResponseMessage result ;
        JSONObject rt = null;

        if (callerService != null)
        {
            String tel = callerService.getOndutyExStaffTelBygridTel(gridTel);

            if ((tel != null) && (tel.compareTo("") != 0))
                rt = JSONObject.parseObject(String.format("{\"tel\":\"%s\"}", tel));
        }
        return (rt==null?JsonResponse.fail(11,"获取电话号码失败!"):JsonResponse.success("获取电话号码成功!",rt));
    }


    @ApiOperation(value = "上传指定文件",notes = "上传指定文件")
    @ResponseBody
    @PostMapping("/upload/{typename}")
    public JsonResponse uploadFile(@PathVariable("typename") String typename,@RequestParam("file") MultipartFile file){
        try {
            if ((header != null) && (callerService != null))
                return callerService.uploadFile(header.getHeader("hosp-ID"), typename,file);
            else
                return JsonResponse.fail(1009, "没有指定医院，上传文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "上传文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "删除指定文件",notes = "删除指定文件")
    @ResponseBody
    @PostMapping("/delete/{filename}")
    public JsonResponse deleteFile(@PathVariable("filename") String filename){
        try {
            if (callerService != null)
                return callerService.deleteFile(filename);
            else
                return JsonResponse.fail(1009, "删除指定文件失败");
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "删除指定文件失败:" + e.getMessage());
        }
    }
}
