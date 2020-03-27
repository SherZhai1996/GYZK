package cn.edu.ujs.lp.intells.basicinformation.service;

import cn.edu.ujs.lp.intells.common.request.Hosp.HospParamsSaveRequest;
import cn.edu.ujs.lp.intells.common.request.Hosp.HospSaveRequest;
import cn.edu.ujs.lp.intells.common.dao.hosp.*;
import cn.edu.ujs.lp.intells.common.entity.Hosp.*;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.service.*;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 医院服务类，实现医院的相关服务
 */
@Service
public class HospService {
    @Autowired
    private DBCommonService dbCommonService;

    @Autowired
    private HospMapper hospMapper;

    @Autowired
    private HospRepository hospRepository;

    //@Autowired
    //private HospFunctionRepository hospFunctionRepository;

    //@Autowired
    //private HospMainRepository hospMainRepository;

    @Autowired
    private BHospParamsRepository hospParamsRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private SerialNumberService serialNumberService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    /**
     上传院徽图片文件
     */
    public JsonResponse uploadHosp_badge(String hospId,MultipartFile hosp_badgefile) throws Exception
    {
        JsonResponse rt;

        try {
            Hosp hosp = getHospbyID(hospId,null);

            if ((fileService != null) && (hosp != null)){

                rt = fileService.fileUpload(hosp.getHospCode(), "basic", hosp_badgefile);

                if (rt.getCode() == 0) {
                    clearHosp_badge(hospId);

                    //hospRepository.updateHosp_badge(hosp.getHospCode(), rt.getData().toString().replace("+", "\\"));
                    hospRepository.updateHosp_badge(hospId, rt.getData().toString());
                    rt = JsonResponse.success("院徽图片保存成功",rt.getData().toString());
                } else {
                    rt = JsonResponse.fail(1006, "院徽图片保存失败");
                }
            } else
                rt = JsonResponse.fail(1006, "院徽图片保存失败");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("院徽图片保存失败:"+e.getMessage());
        }

        return rt;
    }


    /**
     * 删除院徽图片
     * @return
     */
    public JsonResponse clearHosp_badge(String hospId) throws Exception
    {
        JsonResponse rt;

        try {
            Hosp hosp = getHospbyID(hospId,null);
            if ((hospRepository != null) && (hosp != null)){

                if (fileService != null) {
                    rt = fileService.delFile(hosp.getHospBadge());

                    if (rt.getCode()==0)
                        hospRepository.clearHosp_badge(hospId);
                }

                rt = JsonResponse.success("删除院徽图片成功");
            } else
                rt = JsonResponse.fail(1001, "删除院徽图片失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除院徽图片失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 依据ID获取一个完整的医院对象
     * @param id
     * @return
     * @throws Exception
     */
    public Hosp getHospbyID(String id,String hospCode) throws  Exception
    {
        Hosp result=null;

        if (hospMapper != null)
        {
            try
            {
                List<Hosp> lst = hospMapper.getHospbyid(id,hospCode);
                if ((lst != null) && (lst.size()>0)) {
                    result = lst.get(0);
                    result.getPresident(userService);
                    result.getHospPresidentList(userService);
                    result.getHospServicepersons(userService);

                    if (result != null) {
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
                throw new Exception("依据ID，医院编码:["+id+","+hospCode+"]获取医院对象失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 保存医院对象
     * @param hospID
     * @param request
     * @return
     * @throws Exception
     */
    public Hosp saveHosp(String hospID, HospSaveRequest request) throws Exception{

        Hosp hosp = null;
        String leaderId = null;
        List<String> leaderIds = null;
        List<String> servers = null;

        if ((hospRepository != null) && (request != null) && (hospID != null) && (hospID != "")) {

            //医院对象存在性校验
            try {
                if ((hospID != null) && (hospID.length()>0)) hosp = getHospbyID(hospID,null);

                if (hosp != null)
                {
                    leaderId = hosp.getHospPresidentID();
                    leaderIds = hosp.getHospPresidentList();
                    servers = hosp.getHospServicepersons();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception("从数据库获取指定医院对象失败："+e.getMessage());
            }

            if (hosp != null) {
                try {
                    BeanUtils.copyBeanIgnoreNull(hosp,request);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("复制医院对象失败:"+e.getMessage());
                }

                //保存医院对象
                try {
                    Hosp hosp_s = hospRepository.save(hosp); //返回保存对象，浅拷贝
                    hosp.setId(hosp_s.getId());

                    //保存院长角色
                    if ((request.getHospPresidentID() != null) && (request.getHospPresidentID().length()>0) && (request.getHospPresidentID().compareTo(leaderId) != 0) && (userService != null))
                    {
                        if (leaderId != null) userService.DeleteUserRole(leaderId,"005");
                        userService.SetUserRole(request.getHospPresidentID(),"005");
                    }

                    // 保存院长级别人员列表
                    if ((leaderIds != null) && (leaderIds.size()>0) && (userService != null))
                    {
                        for (String userid:leaderIds)
                        {
                            userService.DeleteUserRole(userid,"005");
                        }
                    }

                    if ((request.getHospPresidentList() != null) && (request.getHospPresidentList().size()>0) && (userService != null))
                    {
                        for (String userid:request.getHospPresidentList()) {
                            if (userid != null) {
                                userService.SetUserRole(userid,"005");
                            }
                        }
                    }

                    // 保存一站式服务人员列表
                    if ((servers != null) && (servers.size()>0) && (userService != null)) {
                        for (String userid : servers) {
                            userService.DeleteUserRole(userid, "010");
                        }
                    }

                    if ((request.getHospServicepersons() != null) && (request.getHospServicepersons().size()>0) && (userService != null))
                    {
                        for (String userid:request.getHospServicepersons()) {
                            if (userid != null) {
                                userService.SetUserRole(userid,"010");
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw new Exception("保存医院对象失败:"+e.getMessage());
                }
            }
        }

        return hosp;
    }

    /**
     * 医院参数列表
     * @param hospCode
     * @param hospId
     * @return
     * @throws Exception
     */
    public List<HospParamsBrief> listparams(String hospCode, String hospId) throws Exception {
        List<HospParamsBrief> result= null;

        if (hospMapper != null) {
            try {
                result = hospMapper.listparams(hospCode,hospId);
            } catch (Exception e) {
                throw new Exception("查询医院参数失败:"+e.getMessage());
            }
        }

        return result;
    }

    /**
     * 依据ID获取医院参数对象
     * @param id
     * @return
     * @throws Exception
     */
    public HospParams getHospParamsbyid(String id,String hospId) throws  Exception
    {
        HospParams result=null;

        if (hospMapper != null)
        {
            try
            {
                List<HospParams> lst = hospMapper.getHospParamsbyid(id,hospId);
                if ((lst != null) && (lst.size()>0))
                    result = lst.get(0);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据ID，医院参数ID:["+id+"]获取医院参数对象失败:"+e.getMessage());
            }
        }

        return result;
    }


    /**
     * 依据ID或医院ID删除医院参数对象
     * @param id
     * @param hospId
     * @return
     * @throws Exception
     */
    public boolean deleteHospParams(String id, String hospId) throws  Exception
    {
        boolean result = false ;

        if (hospMapper != null)
        {
            try
            {
                hospMapper.deleteHospParams(id,hospId);
                result = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("删除医院参数对象["+id+"]异常:"+e.getMessage());
            }
        }

        return result ;
    }

    /**
     * 保存医院参数对象
     * @param hospId
     * @param request
     * @return
     * @throws Exception
     */
    public HospParams saveHospParams(String hospId, HospParamsSaveRequest request) throws Exception{

        HospParams hospParams = null;

        if (hospParamsRepository != null) {

            //服务班组存在性校验
            try {
                List<HospParams> ls = (hospMapper!=null?hospMapper.getHospParamsbyid(null,hospId):null);
                if ((ls != null) && (ls.size()>0)) hospParams = ls.get(0);
            }catch (Exception e)
            {
                e.printStackTrace();
                throw  new Exception("从数据库获取指定医院参数失败："+e.getMessage());
            }

            if (hospParams == null) {
                //构造新的医院参数对象
                try {
                    hospParams = HospParams
                            .builder()
                            .clearinspectionmode(request.getClearinspectionmode())
                            .isCenterdispatchedworker(request.isCenterdispatchedworker())
                            .isCenterdispatchedworkerForClearinspection(request.isCenterdispatchedworkerForClearinspection())
                            .isCenterdispatchedworkerForDevice(request.isCenterdispatchedworkerForDevice())
                            .isCenterdispatchedworkerForSecurityinspection(request.isCenterdispatchedworkerForSecurityinspection())
                            .dispatchedworkerinterval(request.getDispatchedworkerinterval())
                            .evaluateinterval(request.getEvaluateinterval())
                            .hospID(hospId)
                            .build();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("创建医院参数对象失败:"+e.getMessage());
                }

            } else {
                try {
                    BeanUtils.copyBeanIgnoreNull(hospParams,request);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    throw  new Exception("复制医院参数对象失败:"+e.getMessage());
                }
            }

            //保存医院参数对象
            try {
                hospParams = hospParamsRepository.save(hospParams);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("保存医院参数对象失败:"+e.getMessage());
            }
        }

        return hospParams;
    }

}
