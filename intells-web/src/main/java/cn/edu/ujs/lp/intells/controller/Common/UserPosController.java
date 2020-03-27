package cn.edu.ujs.lp.intells.controller.Common;

import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPos;
import cn.edu.ujs.lp.intells.systemmanagement.entity.UserPosBrief;
import cn.edu.ujs.lp.intells.systemmanagement.request.UserPosPageRequest;
import cn.edu.ujs.lp.intells.systemmanagement.request.UserPosSaveRequest;
import cn.edu.ujs.lp.intells.systemmanagement.service.UserPosService;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(description = "职工位置跟踪接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/UserPos")
public class UserPosController {

    @Autowired
    private HttpServletRequest header;

    @Autowired
    private UserPosService userPosService;

    @ApiOperation(value = "分页查询",notes = "分页查询")
    @ResponseBody
    @PostMapping(value = "/page")
    public JsonResponse<UserPosBrief> page(@RequestBody @Valid UserPosPageRequest request) {
        try {
            PageResponse response = userPosService.page(request);
            return JsonResponse.success("外委职工位置分分页查询成功", response);
        } catch (Exception e) {
            return JsonResponse.fail(1001, "外委职工位置分分页查询失败:" + e.getMessage());
        }

    }

    @ApiOperation(value = "保存保存外委职工位置信息，新增",notes = "保存保存外委职工位置信息，新增")
    @ResponseBody
    @PostMapping(value = "/save")
    public JsonResponse<UserPosBrief> save(@RequestBody @Valid UserPosSaveRequest request) {
        UserPos userPos = null;

        try {
            if (header != null)
                userPos = userPosService.save(header.getHeader("hosp-ID"), request);
            else
                return JsonResponse.fail(1001, "没有指定医院，保存外委职工位置信息失败");
        } catch (Exception e) {
            return JsonResponse.fail(1001, "保存外委职工位置信息失败:"+e.getMessage());
        }

        if (userPos != null) {
            UserPosBrief ecb = new UserPosBrief();

            BeanUtils.copyBeanIgnoreNull(ecb, userPos);
            return JsonResponse.success("保存外委职工位置信息成功", ecb);
        } else
            return JsonResponse.fail(1001, "保存外委职工位置信息失败!");

    }
}
