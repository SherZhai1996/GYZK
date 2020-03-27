package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import cn.edu.ujs.lp.intells.common.entity.Hosp.HospFunctionBrief;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.ExcelUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 医院对象类，主要用于系统管理
 */
@Entity
@Table(name = "tb_hospital_basic_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospMain extends BaseEntity {

    /**
     * 医院编码
     */
    @Column(name = "hosp_code",length = 20)
    private String hospCode;

    /**
     * 医院名称
     */
    @Column(name = "hosp_name",length = 100)
    private String hospName;

    /**
     * 省份ID
     */
    @Column(name = "pid",length = 32)
    private String Pid;

    /**
     * 市
     */
    @Column(name = "cid",length = 32)
    private String Cid;

    /**
     * 县（市）
     */
    @Column(name = "county_id",length = 32)
    private String CountyId;

    /**
     * 详细地址
     */
    @Column(name = "hosp_detail_address")
    private String hospDetailaddress;

    /**
     * 医院院长，直接填写姓名
     */
    @Column(name = "hosp_president",length = 20)
    private String hospPresident;

    @Column(name = "hosp_president_tel",length = 15)
    private String hospPresidentTel;

    @Column(name = "hosp_president_ID",length = 32)
    private String hospPresidentID;

    /**
     * 法人代表，直接填写姓名
     */
    @Column(name = "hosp_legal_name",length = 20)
    private String hospLegalName;

    /**
     * 中心点(经度)
     */
    @Column(name = "hosp_centerX",columnDefinition = "double(12,7) default '0.00'")
    private Double hospCenterX;

    /**
     * 中心点(纬度)
     */
    @Column(name = "hosp_centerY",columnDefinition = "double(12,7) default '0.00'")
    private Double hospCenterY;

    /**
     * 定位半径(米)
     */
    @Column(name = "hosp_radius")
    private int hospRadius=100;

    /**
     * 医院标志性图片文件名
     */
    @Column(name = "hosp_picture", updatable = false)
    private String hospPicture;

    /**
     * 域名
     */
    //@Column(name = "hosp_url")
    //private String hospUrl;

    /**
     * 医院功能列表
     */
    @Transient
    private List<HospFunctionBrief> hospFunctions;

    /**
     * 医院系统管理员基本信息，登录名缺省为手机号，密码缺省为123456
     */
    @Transient
    private String userId;

    @Transient
    private String userName;

    @Transient
    private String userSex;

    @Transient
    private String userMobile;

    @Transient
    private String hospPicturedisppath;

    @Transient
    private List<String> hospFunctionNames;

    /**
     * 依据负责人ID获取负责人姓名和电话
     * @param userService
     * @throws Exception
     */
    public void getPresident(UserService userService) throws Exception
    {
        if ((userService != null)&&(hospPresidentID != null) && (hospPresidentID != ""))
        {
            try {
                User user = userService.getByID(hospPresidentID);
                hospPresident = user.getUserName();
                hospPresidentTel = user.getUserMobile();
            }
            catch (Exception e)
            {
                throw new Exception("获取物业公司负责人信息失败:"+e.getMessage());
            }
        }
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

    public List<String> gethospFunctionNames() {
        List<String> lst = null;

        if ((hospFunctions != null) && (hospFunctions.size() > 0)) {
            lst = new ArrayList<>();

            for (HospFunctionBrief gfb : hospFunctions) {
                lst.add(gfb.getFunctionName());
            }
        }

        hospFunctionNames = lst;
        return lst;
    }

    public void sethospFunctionsFromStrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<HospFunctionBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            try {
                lst = new ArrayList<>();

                for (String gt : value) {
                    HospFunctionBrief hfb = new HospFunctionBrief();

                    if ((dbCommonService != null) && (gt != null))
                        hfb.setFunctionId(dbCommonService.getHospFunctionidbyName(gt));

                    hfb.setFunctionName(gt);
                    lst.add(hfb);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("设置医院参数对象失败:"+e.getMessage());
            }
        }

        hospFunctions=lst;
    }
}
