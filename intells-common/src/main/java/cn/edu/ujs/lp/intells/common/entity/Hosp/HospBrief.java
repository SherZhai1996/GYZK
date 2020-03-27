package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 医院对象主要信息，用于列表显示
 */
@Data
public class HospBrief {

    private String id;

    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 医院名称
     */
    private String hospName;

    /**
     * 省份ID
     */
    private String Pid;
    private String PidName;

    /**
     * 市
     */
    private String Cid;
    private String CidName;

    /**
     * 县（市）
     */
    private String CountyId;
    private String CountyIdName;

    /**
     * 详细地址
     */
    private String hospDetailaddress;

    /**
     * 医院院长，直接填写姓名
     */
    private String hospPresident;

    /**
     * 法人代表，直接填写姓名
     */
    private String hospLegalName;

    /**
     * 中心点(经度)
     */
    private Double hospCenterX = BaiduUtils.defaultCenterX;

    /**
     * 中心点(纬度)
     */
    private Double hospCenterY = BaiduUtils.defaultCenterY;

    private String hospBadge;

    private String hospPicture;

    private String hospBadgedisppath;

    private String hospPicturedisppath;

    private String hospServeCenterTel;

    /**
     * 备注，用于存放行政区划详细地址
     */
    private String remark;

    /**
     * 医院系统管理员基本信息，登录名缺省为手机号，密码缺省为123456
     */
    private String userId;

    private String userName;

    private String userSex;

    private String userMobile;

    /**
     * 获取医院院徽图片显示路径
     * @return
     */
    public String getHospBadgedisppath()
    {
        String result = null;

        if ((hospBadge != null) && (hospBadge.trim().length()>0))
            result = hospBadge;
        else
            result = OSUtils.getHosplogopicfile();

        hospBadgedisppath = result ;
        return result;
    }

    /**
     * 获取医院标志性图片显示相对路径
     * @return
     */
    public String getHospPicturedisppath()
    {
        String result = null;

        if ((hospPicture != null) && (hospPicture.trim().length()>0))
            result = hospPicture;
        else
            result = OSUtils.getHosppicfile();

        hospPicturedisppath = result ;
        return result;
    }

    /**
     * 获取医院管理员信息
     * @param userService
     * @throws Exception
     */
    public void getManagementUser(UserService userService) throws Exception
    {
        if ((userService != null)&&(!ExcelUtils.isNullofString(userId)))
        {
            try {
                User user = userService.getByID(userId);
                userName = user.getUserName();
                userMobile = user.getUserMobile();
            }
            catch (Exception e)
            {
                throw new Exception("获取医院管理员信息失败:"+e.getMessage());
            }
        }
    }

    /**
     * 依据行政区划获取医院地址
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getHospDetailaddress(DataDictionaryService dataDictionaryService) throws Exception
    {
        String result = null;

        if ((this.getRemark() == null) || (this.getRemark() == ""))
        {
            if (dataDictionaryService != null)
            {
                try
                {
                    if ((Pid != null) && (Pid != "")) {
                        result = dataDictionaryService.getDataDictionary("db_Province").getNamebyID(Pid);

                        if ((Cid != null) && (Cid != ""))
                        {
                            result = result+dataDictionaryService.getDataDictionary("db_District").getNamebyID(Cid);

                            if ((CountyId != null) && (CountyId != ""))
                            {
                                result = result+dataDictionaryService.getDataDictionary("db_County").getNamebyID(CountyId);
                            }
                        }
                    }
                    else
                        result = BaiduUtils.defaultAddr;
                }
                catch (Exception e)
                {
                    throw new Exception("由行政区划编码获取详细地址失败："+e.getMessage());
                }
            }
        }

        this.setRemark(result);
        if (ExcelUtils.isNullofString(hospDetailaddress))  hospDetailaddress = this.getRemark();

        return result;
    }

    /**
     * 获取省直辖市名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getPidName(DataDictionaryService dataDictionaryService) throws Exception
    {
        String result = null;

        if ((Pid == null) || (Pid.length() > 0))
        {
            if (dataDictionaryService != null)
            {
                try
                {
                    result = dataDictionaryService.getDataDictionary("db_Province").getNamebyID(Pid);
                }
                catch (Exception e)
                {
                    throw new Exception("由行政区划编码获取详细地址失败："+e.getMessage());
                }
            }
        }
        else result = BaiduUtils.defaultProvinceName;

        PidName = result;
        return PidName;
    }

    /**
     * 获取地市名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getCidName(DataDictionaryService dataDictionaryService) throws Exception
    {
        String result = null;

        if ((Cid == null) || (Cid.length() > 0))
        {
            if (dataDictionaryService != null)
            {
                try
                {
                    result = dataDictionaryService.getDataDictionary("db_District").getNamebyID(Cid);
                }
                catch (Exception e)
                {
                    throw new Exception("由行政区划编码获取详细地址失败："+e.getMessage());
                }
            }
        }

        CidName = result;
        return CidName;
    }

    /**
     * 获取县市名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getCountyIdName(DataDictionaryService dataDictionaryService) throws Exception
    {
        String result = null;

        if ((CountyId == null) || (CountyId.length() > 0))
        {
            if (dataDictionaryService != null)
            {
                try
                {
                    result = dataDictionaryService.getDataDictionary("db_County").getNamebyID(CountyId);
                }
                catch (Exception e)
                {
                    throw new Exception("由行政区划编码获取详细地址失败："+e.getMessage());
                }
            }
        }

        CountyIdName = result;
        return CountyIdName;
    }

}
