package cn.edu.ujs.lp.intells.systemmanagement.service;

import cn.edu.ujs.lp.intells.common.dao.User.UserRepository;
import cn.edu.ujs.lp.intells.common.dao.hosp.*;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.*;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.systemmanagement.dao.HospFunctionRepository;
import cn.edu.ujs.lp.intells.systemmanagement.dao.HospMainRepository;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospFunction;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospMain;
import cn.edu.ujs.lp.intells.systemmanagement.request.HospMainSaveRequest;
import cn.edu.ujs.lp.intells.common.request.Hosp.HospPageRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.response.PageResponse;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import com.github.pagehelper.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 医院服务类，实现医院的相关服务
 */
@Service
public class HospMainService {
    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private HospMapper hospMapper;

    @Autowired
    private HospRepository hospRepository;

    @Autowired
    private HospFunctionRepository hospFunctionRepository;

    @Autowired
    private HospMainRepository hospMainRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

     /**
     上传医院标志性图片文件
     */
    public JsonResponse uploadHosp_picture(String hospId,MultipartFile hosp_picturefile) throws Exception
    {
        JsonResponse rt;

        try {
            HospMain hospMain = getHospMainbyID(hospId, null);
            if ((fileService != null)&&(hospMain != null)) {
                rt = fileService.fileUpload(hospMain.getHospCode(), "basic", hosp_picturefile);

                if (rt.getCode() == 0) {
                    clearHosp_picture(hospId);

                    hospRepository.updateHosp_picture(hospId, rt.getData().toString());
                    rt = JsonResponse.success("医院标志性图片保存成功!",rt.getData().toString());
                } else {
                    rt = JsonResponse.fail(1006, "医院标志性图片上传失败");
                }
            } else
                rt = JsonResponse.fail(1006, "医院标志性图片保存失败:文件服务为null或获取医院管理对象失败");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("医院标志性图片保存失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除医院标志性图片
     * @return
     */
    public JsonResponse clearHosp_picture(String hospId) throws Exception
    {
        JsonResponse rt;

        try {
            HospMain hospMain = getHospMainbyID(hospId, null);

            if ((hospRepository != null) && (hospMain != null) && (fileService != null)) {
                rt = fileService.delFile(hospMain.getHospPicture());

                if (rt.getCode() == 0)   hospRepository.clearHosp_picture(hospId);

                rt = JsonResponse.success("删除医院标志性图片成功");
            } else
                rt = JsonResponse.fail(1004, "删除医院标志性图片失败:文件服务为null或获取医院管理对象失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除医院标志性图片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 分页查找
     * @param request
     * @return
     */
    public PageResponse<HospBrief> page(HospPageRequest request) throws Exception
    {
        try {
            Page<HospBrief> page = hospMapper.page(request, new RowBounds(request.getIndex(), request.getSize()));
            List<Long> lst = hospMapper.Rcount(request);
            Long total = 0L;
            if ((lst!=null) && (lst.size()>0))
                total = lst.get(0);

            if ((page != null) && (page.size()>0))
            {
                for (HospBrief hb:page)
                {
                    hb.getPidName(dataDictionaryService);
                    hb.getCidName(dataDictionaryService);
                    hb.getCountyIdName(dataDictionaryService);
                    hb.getHospDetailaddress(dataDictionaryService);

                    double p1x = hb.getHospCenterX();
                    double p1y = hb.getHospCenterY();

                    String[] pc = dataDictionaryService.getCoordinate(hb.getRemark()!=null?hb.getRemark():BaiduUtils.defaultAddr);
                    if ((pc != null) && (pc.length>=2))
                    {
                        double p2x = Double.parseDouble(pc[0]);
                        double p2y = Double.parseDouble(pc[1]);

                        //判断是否合规的中心坐标，不合规的设置为默认值
                        if (BaiduUtils.getDistance(p1x,p1y,p2x,p2y)>BaiduUtils.defaultProvinceRadius)
                        {
                            hb.setHospCenterX(p2x);
                            hb.setHospCenterY(p2y);
                        }
                    }
                    else
                    {
                        hb.setHospCenterX(BaiduUtils.defaultCenterX);
                        hb.setHospCenterY(BaiduUtils.defaultCenterY);
                    }
                }
            }

            return new PageResponse<HospBrief>(page, request, total);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("医院分页查询失败："+e.getMessage());
        }
    }

    /**
     * 依据ID或医院编码获取医院管理对象
     * @param id
     * @param hospCode
     * @return
     * @throws Exception
     */
    public HospMain getHospMainbyID(String id, String hospCode) throws  Exception
    {
        HospMain result=null;

        if (hospMapper != null)
        {
            try
            {
                List<HospMain> lst = hospMapper.getHospMainbyid(id,hospCode);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);

                    if (result != null)
                    {
                        result.getPresident(userService);
                        result.getManagementUser(userService);
                        result.getHospDetailaddress(dataDictionaryService);

                        double p1x = result.getHospCenterX();
                        double p1y = result.getHospCenterY();

                        String[] pc = dataDictionaryService.getCoordinate(result.getRemark()!=null?result.getRemark():BaiduUtils.defaultAddr);
                        if ((pc != null) && (pc.length>=2))
                        {
                            double p2x = Double.parseDouble(pc[0]);
                            double p2y = Double.parseDouble(pc[1]);

                            //判断是否合规的中心坐标，不合规的设置为默认值
                            if (BaiduUtils.getDistance(p1x,p1y,p2x,p2y)>BaiduUtils.defaultProvinceRadius)
                            {
                                result.setHospCenterX(p2x);
                                result.setHospCenterY(p2y);
                            }
                        }
                        else
                        {
                            result.setHospCenterX(BaiduUtils.defaultCenterX);
                            result.setHospCenterY(BaiduUtils.defaultCenterY);
                        }

                        if (Math.abs(result.getHospRadius()-BaiduUtils.defaulthospRadius)>(BaiduUtils.defaulthospRadius/2))
                            result.setHospRadius(BaiduUtils.defaulthospRadius);
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID，医院编码:["+id+","+hospCode+"]获取医院管理对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 依据医院ID或医院编码删除医院对象，同时删除其关联对象
     * @param id
     * @param hospCode
     * @return
     * @throws Exception
     */
    public boolean delete(String id, String hospCode) throws  Exception
    {
        boolean result = false ;

        if (hospMapper != null)
        {
            try
            {
                hospMapper.deleteHospParams(null,id);
                hospMapper.deleteHospFunctionlist(null,id);
                hospMapper.delete(id,hospCode);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除医院对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存医院管理对象
     * @param request
     * @return
     * @throws Exception
     */
    public HospMain saveHospMain(HospMainSaveRequest request) throws Exception{

        HospMain hospMain = null;
        String leaderId = null;

        if ((dbCommonService!=null) && (hospMainRepository != null)&&(request != null) && (!ExcelUtils.isNullofString(request.getHospName()))) {

            //医院管理对象存在性校验
            try {
                if (request.getId() != null) hospMain = getHospMainbyID(request.getId(),null);

                if (hospMain == null) {
                    List<HospMain> ls = (hospMapper != null ? hospMapper.getHospMainbyName(request.getHospName()) : null);
                    if ((ls != null) && (ls.size() > 0)) hospMain = ls.get(0);
                }

                if (hospMain != null) leaderId = hospMain.getUserId(); //获取原有医院管理员
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("从数据库获取指定医院管理对象失败：" + e.getMessage());
            }

            if (hospMain == null) {
                //构造新的医院管理对象
                try {
                    hospMain = HospMain
                            .builder()
                            .build();

                    hospMain.setHospCode(serialNumberService != null ? serialNumberService.getHospCode() : null);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("创建医院管理对象失败:" + e.getMessage());
                }
            }

            //复制对象
            if (hospMain != null) {
                try {
                    BeanUtils.copyBeanIgnoreNull(hospMain, request);
                    hospMain.sethospFunctionsFromStrings(request.getHospFunctionNames(), dbCommonService);
                    if (ExcelUtils.isNullofString(request.getUserId())) hospMain.setUserId(null);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("复制医院管理对象失败:" + e.getMessage());
                }
            }

            //保存医院管理对象
            if (hospMain != null) {
                try {
                    HospMain hospMain_s = hospMainRepository.save(hospMain);
                    hospMain.setId(hospMain_s.getId());

                    hospMapper.deleteHospFunctionlist(null, hospMain.getId());

                    //保存函数列表对象
                    if((hospMain.getHospFunctions() != null) && (hospMain.getHospFunctions().size()>0)) {
                        List<HospFunction> lst = new ArrayList<>();

                        for (HospFunctionBrief hf : hospMain.getHospFunctions()) {
                            HospFunction hospFunction = HospFunction
                                    .builder()
                                    .hospId(hospMain.getId())
                                    .functionId(hf.getFunctionId())
                                    .build();

                            lst.add(hospFunction);
                        }

                        hospFunctionRepository.saveAll(lst);
                    }

                    //保存医院管理员
                    if (( !ExcelUtils.isNullofString(request.getUserId())) && (userService != null))
                    {
                        if ((!ExcelUtils.isNullofString(leaderId)) && (request.getUserId().compareTo(leaderId) != 0)) {
                            userService.DeleteUserHospManageRole(leaderId,hospMain.getId());
                            userService.SetUserHospManageRole(request.getUserId(),hospMain.getId());
                        }
                        if (ExcelUtils.isNullofString(leaderId)) userService.SetUserHospManageRole(request.getUserId(),hospMain.getId());
                    }

                    if (ExcelUtils.isNullofString(request.getUserId())) {
                        if (leaderId != null) userService.DeleteUserHospManageRole(leaderId,hospMain.getId());
                    }

                    //判断是否需要新建部门、网格、更根节点

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("保存医院管理对象失败:" + e.getMessage());
                }
            }
        }

        return hospMain;
    }

}
