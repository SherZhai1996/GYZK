package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.entity.User.UserBrief;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 医院信息表
 *
 * @author Meredith
 * @date 2019-09-16
 */
@Entity
@Table(name = "tb_hospital_basic_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hosp extends BaseEntity {

    /**
     * 医院编码
     */
    @Column(name = "hosp_code",length = 20, updatable = false)
    private String hospCode;

    /**
     * 医院名称
     */
    @Column(name = "hosp_name",length = 100, updatable = false)
    private String hospName;

    /**
     * 登记证号
     */
    @Column(name = "hosp_certificate_number",length = 30)
    private String hospCertificateNumber;

    /**
     * 创立时间
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "hosp_build_date")
    private Date hospBuildDate;

    /**
     * 编制人数
     */
    @Column(name = "hosp_authorized_strength",length = 6)
    private Integer hospAuthorizedStrength;

    /**
     * 建筑面积(m²)
     */
    @Column(name = "hosp_build_area",columnDefinition = "double(7,1) default '0.0'")
    private Double hospBuildArea;

    /**
     * 门急诊面积(m²)
     */
    @Column(name = "hosp_emergency_area",columnDefinition = "double(7,1) default '0.0'")
    private Double hospEmergencyArea;

    /**
     * 医院床位数
     */
    @Column(name = "hosp_bed_amount",length = 6)
    private Integer hospBedAmount;

    /**
     * 日门诊量
     */
    @Column(name = "hosp_outpatient_volume",length = 6)
    private Integer hospOutpatientVolume;

    /**
     * 省份ID
     */
    @Column(name = "pid",length = 32, updatable = false)
    private String Pid;

    /**
     * 市
     */
    @Column(name = "cid",length = 32, updatable = false)
    private String Cid;

    /**
     * 县（市）
     */
    @Column(name = "county_id",length = 32, updatable = false)
    private String CountyId;

    /**
     * 医院详细地址
     */
    @Column(name = "hosp_detail_address")
    private String hospDetailaddress;

    /**
     * 设计车位数
     */
    @Column(name = "hosp_parking_amount",length = 7)
    private Integer hospParkingAmount;

    /**
     * 驻院民警数
     */
    @Column(name = "hosp_police_amount",length = 5)
    private Integer hospPoliceAmount;

    /**
     * 保安数量
     */
    @Column(name = "hosp_security_amount",length = 5)
    private Integer hospSecurityAmount;

    /**
     * 保安公司名称
     */
    @Column(name = "hosp_security_company_id",length = 32)
    private String hospSecurityCompanyId;

    /**
     * 医院院长，直接填写姓名
     */
    @Column(name = "hosp_president")
    private String hospPresident;

    @Column(name = "hosp_president_tel",length = 15)
    private String hospPresidentTel;

    @Column(name = "hosp_president_ID",length = 32)
    private String hospPresidentID;

    /**
     * 主要代表人，直接填写姓名
     */
    @Column(name = "hosp_representative")
    private String hospRepresentative;

    /**
     * 法人代表，直接填写姓名
     */
    @Column(name = "hosp_legal_name",length = 20)
    private String hospLegalName;

    /**
     * 主要领导团队，直接填写姓名
     */
    @Column(name = "hosp_main_leadership_team")
    private String hospLeadershipTeam;

    /**
     * 总务处长姓名，直接填写姓名
     */
    @Column(name = "hosp_affair_chief",length = 20)
    private String hospAffairChief;

    /**
     * 总务处长手机号码
     */
    @Column(name = "hosp_affair_chief_mobile",length = 15)
    private String hospAffairChiefMobile;

    /**
     *总务处长办公电话
     */
    @Column(name = "hosp_affair_chief_tel",length = 15)
    private String hospAffairChiefTel;

    /**
     * 总务处长邮箱
     */
    @Column(name = "hosp_affair_chief_email",length = 100)
    private String hospAffairChiefEmail;

    /**
     * 保卫处长姓名，直接填写姓名
     */
    @Column(name = "hosp_security_chief",length = 20)
    private String hospSecurityChief;

    /**
     * 保卫处长手机号码
     */
    @Column(name = "hosp_security_chief_mobile",length = 15)
    private String hospSecurityChiefMobile;

    /**
     * 医院院徽图片文件名
     */
    @Column(name = "hosp_badge", updatable = false)
    private String hospBadge;

    /**
     * 医院标志性图片文件名
     */
    @Column(name = "hosp_picture", updatable = false)
    private String hospPicture;

    /**
     * 医院简介
     */
    @Column(name = "hosp_introduction")
    private String hospIntroduction;

    /**
     * 中心点(纬度)
     */
    @Column(name = "hosp_serve_center_tel", length = 15)
    private String hospServeCenterTel;

    /**
     * 中心点(经度)
     */
    @Column(name = "hosp_centerX",columnDefinition = "double(12,7) default '0.00'",updatable = false)
    private Double hospCenterX = BaiduUtils.defaultCenterX;

    /**
     * 中心点(纬度)
     */
    @Column(name = "hosp_centerY",columnDefinition = "double(12,7) default '0.00'",updatable = false)
    private Double hospCenterY = BaiduUtils.defaultCenterY;

    /**
     * 定位半径(米)
     */
    @Column(name = "hosp_radius",updatable = false)
    private int hospRadius = BaiduUtils.defaulthospRadius;

    /**
     * 医院功能列表
     */
    @Transient
    private List<HospFunctionBrief> hospFunctions;


    @Transient
    private String hospBadgedisppath;

    @Transient
    private String hospPicturedisppath;

    /**
     * 院级管理员列表
     */
    @Transient
    private List<String> hospPresidentList;

    /**
     * 一站式服务人员列表
     */
    @Transient
    private List<String> hospServicepersons;

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
                if (user != null) {
                    if (user.getUserName() != null) hospPresident = user.getUserName();
                    if (user.getUserMobile() != null) hospPresidentTel = user.getUserMobile();
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取医院院长信息失败:"+e.getMessage());
            }
        }
    }

    /**
     * 获取医院院长级角色列表
     * @param userService
     * @return
     * @throws Exception
     */
    public List<String> getHospPresidentList(UserService userService) throws Exception
    {
        List<String> result = null;

        if (userService != null)
        {
            try {
                if ((this.getId() != null) && (this.getId().length() > 0)) {
                    List<UserBrief> users = userService.findByRole(this.getId(), "005");

                    if ((users != null) && (users.size() > 0)) {
                        result = new ArrayList<>();

                        for (UserBrief user : users) {
                            result.add(user.getId());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取医院院长级角色人员列表失败:"+e.getMessage());
            }
        }

        hospPresidentList = result;
        return result;
    }

    /**
     * 获取一站式服务人员列表
     * @param userService
     * @return
     * @throws Exception
     */
    public List<String> getHospServicepersons(UserService userService) throws Exception
    {
        List<String> result = null;

        if (userService != null)
        {
            try {
                if ((this.getId() != null) && (this.getId().length() > 0)) {
                    List<UserBrief> users = userService.findByRole(this.getId(), "010");

                    if ((users != null) && (users.size() > 0)) {
                        result = new ArrayList<>();

                        for (UserBrief user : users) {
                            result.add(user.getId());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取医院一站式服务人员人员列表失败:"+e.getMessage());
            }
        }

        hospServicepersons = result ;
        return result;
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
        if ((hospDetailaddress == null) || (hospDetailaddress == ""))  hospDetailaddress = this.getRemark();

        return result;
    }

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

    public List<String> gethospFunctionNames() {
        List<String> lst = null;

        if ((hospFunctions != null) && (hospFunctions.size() > 0)) {
            lst = new ArrayList<>();

            for (HospFunctionBrief gfb : hospFunctions) {
                lst.add(gfb.getFunctionName());
            }
        }

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
