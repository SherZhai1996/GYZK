package cn.edu.ujs.lp.intells.controller.Common;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Staff;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffPageRequest;
import cn.edu.ujs.lp.intells.basicinformation.request.hosp.StaffSaveRequest;
import cn.edu.ujs.lp.intells.basicinformation.service.StaffService;
import cn.edu.ujs.lp.intells.common.entity.Hosp.StaffBrief;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.entity.User.UserBrief;
import cn.edu.ujs.lp.intells.common.request.User.UserPageRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserSaveRequest;
import cn.edu.ujs.lp.intells.common.request.User.UserSetPWRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.UserService;
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

@Api(description = "系统用户接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/User")
public class UserController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private UserService userService;

    @Autowired
    private DBCommonService dbCommonService;

    @ApiOperation(value = "下载系统Excel模板",notes = "下载系统Excel模板")
    @ResponseBody
    @GetMapping(value = "/template")
    public JsonResponse template(HttpServletResponse response){
        try
        {
            return userService.exportExcelTemplate(response);
        }
        catch (Exception e)
        {
            return JsonResponse.fail(1009,"系统用户Excel模板下载失败");
        }
    }

    @ApiOperation(value = "上传系统Excel文件",notes = "上传系统Excel文件")
    @ResponseBody
    @PostMapping("/upload")
    public JsonResponse upload(@RequestParam("file") MultipartFile file){
        try {
            //if (header != null)
            //    return userService.ImportExcelData(header.getHeader("hosp-ID"), file);
            //else
            return userService.ImportExcelData(null, file);
        }
        catch (Exception e) {
            return JsonResponse.fail(1009, "系统用户Excel文件失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "导出系统用户Excel数据",notes = "导出系统用户Excel数据")
    @ResponseBody
    @GetMapping(value = "/export")
    public JsonResponse export(HttpServletResponse response) {

        try {
            return userService.exportExcelData(response);
        } catch (Exception e) {
            return JsonResponse.fail(1009, "导出系统用户Excel数据失败:" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询所有系统用户",notes = "查询所有系统用户")
    @ResponseBody
    @PostMapping(value = "/list/{userName}/{userMobile}")
    public JsonResponse list(@PathVariable("userName") String userName,@PathVariable("userMobile") String userMobile) {
        List<UserBrief> result;

        try {
            UserPageRequest request = new UserPageRequest();
            //request.setHospId(header != null?header.getHeader("hosp-ID").trim():null);
            request.setHospId(null);
            request.setUserName(userName.trim());
            request.setUserMobile(userMobile.trim());

            result = userService.list(null,request);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "查询所有系统用户失败:"+e.getMessage());
        }

        return JsonResponse.success("获取所有系统用户成功", result);
    }

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<UserBrief> page(@RequestBody @Valid UserPageRequest request) {
        PageResponse response = null;

        try {
            //if (header != null) {
            //    response = userService.page(header.getHeader("hosp-ID"), request);
            //} else
            response = userService.page(null, request);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "系统用户分页查询失败:" + e.getMessage());
        }

        return JsonResponse.success("系统用户分页查询成功", response);
    }

    @ApiOperation(value = "获取指定ID的系统用户完整信息",notes = "获取指定ID的系统用户完整信息")
    @ResponseBody
    @GetMapping(value = "/getByID/{id}")
    public JsonResponse getByID(@PathVariable("id") String id){
        if (userService != null)
        {
            try {
                User ec = userService.getByID(id);
                return JsonResponse.success("获取指定ID的系统用户完整信息成功",ec);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"获取指定ID的系统用户完整信息失败："+e.getMessage());
            }
        }
        else
            return JsonResponse.fail(1001,"获取指定ID的系统用户完整信息失败");
    }


    @ApiOperation(value = "删除指定ID的系统用户",notes = "删除指定ID的系统用户")
    @ResponseBody
    @GetMapping(value = "/delete/{id}")
    public JsonResponse delete(@PathVariable("id") String id) {
        boolean result;

        try {
            result = userService.delete(id);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "删除指定ID的系统用户信息失败:"+e.getMessage());
        }

        return JsonResponse.success("删除指定ID的系统用户信息成功", id);
    }


    @ApiOperation(value = "保存一个系统用户信息，可以是新增或更新",notes = "保存一个系统用户信息，可以是新增或更新")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<StaffBrief> save(@RequestBody @Valid UserSaveRequest request) {
        User user = null;

        try {
            //if (header != null)
             //   user = userService.save(header.getHeader("hosp-ID"), request);
            //else
            user = userService.save(null, request);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "系统用户保存失败:"+e.getMessage());
        }

        if (user != null) {
            return JsonResponse.success("保存系统用户信息成功", user);
        } else
            return JsonResponse.fail(1001, "保存系统用户信息失败!");

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

    @ApiOperation(value = "设置用户密码",notes = "设置用户密码")
    @ResponseBody
    @PostMapping(value = "/SetUserPassword")
    public JsonResponse SetUserPassword(@RequestBody @Valid UserSetPWRequest userSetPWRequest){
        String result = null;

        if ((userService != null)&&(userSetPWRequest != null))
        {
            try {
                result = userService.SetUserPassword(userSetPWRequest);
            }
            catch (Exception e)
            {
                return JsonResponse.fail(1001,"设置用户密码失败:"+e.getMessage());
            }
        }

        if (result != null)
            return JsonResponse.fail(1001,"设置用户密码失败:"+result);
        else
            return JsonResponse.success("设置用户密码成功");
    }

    @ApiOperation(value = "回复初始密码",notes = "回复初始密码")
    @ResponseBody
    @PostMapping("/ResetUserpassword/{userId}")
    public JsonResponse ResetUserpassword(@PathVariable("userId") String userId) {
        try {
            if (userService != null)
                if (userService.ResetUserpassword(userId))
                    return JsonResponse.success("回复初始密码成功");
                else
                    return JsonResponse.fail(1009,"回复初始密码失败");
            else
                return JsonResponse.fail(1009, "服务启动失败，回复初始密码失败");
        } catch (Exception e) {
            return JsonResponse.fail(1009, "回复初始密码失败:" + e.getMessage());
        }
    }

}
