package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.entity.User.UserGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 外委职工数据对象
 */
@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exstaff  extends BaseHospEntity {
    @Column(name = "user_name",length = 20)
    private String exstaffName; //` varchar(20) NOT NULL COMMENT '姓名',

    @Column(name = "user_sex",length = 32)
	private String exstaffSex; //` varchar(32) DEFAULT NULL COMMENT '性别(数据字典)',

    @Column(name = "user_mobile",length = 15)
	private String exstaffMobile; //` varchar(15) NOT NULL COMMENT '手机号',

    @Column(name = "exstaff_jobno",length = 20)
	private String exstaffJobno; //` varchar(20) DEFAULT NULL COMMENT '职工工号',

    @Column(name = "exstaff_jobstate",length = 32)
	private String exstaffJobstate; //` varchar(32) DEFAULT NULL COMMENT '岗位状态(数据字典, db_job_state)',

    @Transient
    private String exstaffJobstateName;

    @Column(name = "user_activestate")
	private String exstaffActivestate; //` tinyint(1) DEFAULT 0 COMMENT '激活状态(1--激活, 0--未激活)',

    @Transient
    private String exstaffActivestateName;

    @Column(name = "excompany_id",length = 32)
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联）',

    @Transient
    private String excompanyName;

    @Column(name = "exteam_id",length = 32)
	private String exteamId; //` varchar(32) NOT NULL COMMENT '所属班组（外键关联）',

    @Transient
    private String exteamName;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "exstaff_employstartdate")
	private Date exstaffEmploystartdate; //` datetime DEFAULT NULL COMMENT '聘用日期',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "exstaff_employenddate")
    private Date exstaffEmployenddate; //` datetime DEFAULT NULL COMMENT '终止日期',

    @Column(name = "exstaff_pic",length = 400,updatable = false)
    private String exstaffPic; //` varchar(400) DEFAULT NULL COMMENT '照片图片文件名',

    /**
     * 技术工种类型
     */
    @Column(name = "exstaff_technicalwork",length = 32)
    private String exstaffTechnicalwork; //` varchar(32) DEFAULT  NULL COMMENT '技术工种(数据字典)',

    /**
     * 专业技能描述
     */
    @Column(name = "exstaff_expertise",length = 20)
    private String exstaffExpertise; //` varchar(400) DEFAULT  NULL COMMENT '专用技能，文字描述',

    @Column(name = "exstaff_leadname",length = 50)
	private String exstaffLeadname; //` varchar(50) DEFAULT  NULL COMMENT '直属领导',

    @Column(name = "exstaff_stature")
    private double exstaffStature; //` double(7,1) DEFAULT  NULL COMMENT '身高',

    @Column(name = "exstaff_weight")
    private double exstaffWeight; //` double(7,1) DEFAULT  NULL COMMENT '体重',

    @Column(name = "exstaff_shoesize")
    private double exstaffShoesize; //` double(7,1) DEFAULT  NULL COMMENT '鞋号',

    @Column(name = "exstaff_emercontactname",length = 20)
    private String exstaffEmercontactname; //` varchar(20) DEFAULT  NULL COMMENT '紧急联系人姓名',

    @Column(name = "exstaff_emercontacttel",length = 15)
    private String exstaffEmercontacttel; //` varchar(15) DEFAULT  NULL COMMENT '紧急联系电话',

    @Column(name = "exstaff_emercontactrelationship",length = 100)
    private String exstaffEmercontactrelationship; //` varchar(100) DEFAULT  NULL COMMENT '紧急联系人关系',

    @Column(name = "user_login_name",length = 20)
    private String exstaffLoginname; //` varchar(20)  NOT NULL COMMENT '登录用户名,缺省时为手机号，自动填入',

    @Column(name = "user_login_nickname",length = 20)
    private String exstaffLoginnickname; //` varchar(20)  DEFAULT NULL COMMENT '用户昵称',

    @Column(name = "user_password",length = 32)
    private String exstaffLoginpassword; //` varchar(32)  DEFAULT  NULL COMMENT '登录密码,MD5密文存放',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "exstaff_birthday")
	private Date exstaffBirthday; //` datetime DEFAULT NULL COMMENT '出生日期',

    @Column(name = "exstaff_tel",length = 15)
    private String exstaffTel; //` varchar(15) DEFAULT NULL COMMENT '办公电话',

    @Column(name = "exstaff_email",length = 200)
    private String exstaffEmail; //` varchar(200) DEFAULT  NULL COMMENT '电子邮箱',

    @Column(name = "exstaff_nation",length = 32)
    private String exstaffNation; //` varchar(32) DEFAULT  NULL COMMENT '民族(数据字典)',

    @Column(name = "exstaff_native_place",length = 100)
    private String exstaffNativeplace; //` varchar(100) DEFAULT  NULL COMMENT '籍贯',

    @Column(name = "exstaff_marriage_state",length = 32)
    private String exstaffMarriagestate; //` varchar(32) DEFAULT  NULL COMMENT '婚姻状况(数据字典)',

    @Column(name = "exstaff_identify_id",length = 20)
    private String exstaffIdentifyid; //` varchar(20) DEFAULT  NULL COMMENT '身份证号码',

    @Column(name = "exstaff_degree",length = 32)
	private String exstaffDegree; //` varchar(32) DEFAULT NULL COMMENT '学历(数据字典)',

    @Column(name = "exstaff_weixin_id",length = 100)
    private String exstaffWeixinid; //` varchar(100) DEFAULT  NULL COMMENT '微信id',

    @Column(name = "exstaff_qq_id",length = 20)
    private String exstaffQqid; //` varchar(20) DEFAULT  NULL COMMENT 'qqid',

    @Column(name = "exstaff_address",length = 250)
    private String exstaffAddress; //` varchar(255) DEFAULT  NULL COMMENT '联系地址',

    @Column(name = "user_type")
    private String userType;

    /**
     * 全名
     */
    @Transient
    private String fullName;

    /**
     * 技术证书集
     */
    @Transient
    private List<ExstaffskillcertificateBrief> exstaffskillcertificates;

    /**
     * 技术证书编号
     */
    @Transient
    private List<String> exstaffskillcertificateIds;

    /**
     * 技术证书图片
     */
    @Transient
    private List<String> exstaffskillcertificatePics;

    public List<String> getExstaffskillcertificateIdsFromDB() {
        List<String> lst = null;

        if ((exstaffskillcertificates != null) && (exstaffskillcertificates.size() > 0)) {
            lst = new ArrayList<>();

            for (ExstaffskillcertificateBrief gtb : exstaffskillcertificates) {
                if ((gtb.getSkillcertificateId() != null) && (gtb.getSkillcertificateId() != ""))
                    lst.add(gtb.getSkillcertificateId());
            }
        }

        exstaffskillcertificateIds = lst;
        return lst;
    }

    public List<String> getExstaffskillcertificatePicsFromDB() {
        List<String> lst = null;

        if ((exstaffskillcertificates != null) && (exstaffskillcertificates.size() > 0)) {
            lst = new ArrayList<>();

            for (ExstaffskillcertificateBrief gtb : exstaffskillcertificates) {
                if ((gtb.getSkillcertificatePic() != null) && (gtb.getSkillcertificatePic() != ""))
                    lst.add(gtb.getSkillcertificatePic());
            }
        }

        exstaffskillcertificatePics = lst;
        return lst;
    }

    /**
     * 餐饮证书集
     */
    //@Transient
    //private List<ExstaffrestaurantcertificateBrief> exstaffrestaurantcertificates;

    /**
     * 员工角色
     */
    @Transient
    private List<UserRoleBrief> roleIds;

    @Transient
    private List<String> roleStrings;

    /**
     * 获取全名
     * @return
     */
    public String getFullName()
    {
        return getRemark();
    }

    public List<String> getRoleStrings() {
        List<String> lst = null;

        if ((roleIds != null) && (roleIds.size() > 0)) {
            lst = new ArrayList<>();

            for (UserRoleBrief srb : roleIds) {
                lst.add(srb.getRoleName());
            }
        }

        return lst;
    }

    public void setRolesFromStrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<UserRoleBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            lst = new ArrayList<>();

            try {
                for (String rn : value) {
                    UserRoleBrief srb = new UserRoleBrief();

                    if ((dbCommonService != null) && (rn != null)) {
                        String rid = dbCommonService.getRoleidbyName(rn);

                        if (rid != null) {
                            srb.setRoleId(rid);
                            srb.setRoleName(rn);
                            lst.add(srb);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("设置角色对象失败："+e.getMessage());
            }
        }

        roleIds=lst;
    }

    /**
     * 员工的责任网格
     */
    @Transient
    private List<UserGridBrief> gridIds;

    @Transient
    private List<String> gridNameStrings;

    public List<String> getGridNameStrings() {
        List<String> lst = null;

        if ((gridIds != null) && (gridIds.size() > 0)) {
            lst = new ArrayList<>();

            for (UserGridBrief gtb : gridIds) {
                lst.add(gtb.getGridFullname());
            }
        }

        return lst;
    }

    public void setGridIdsFromStrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<UserGridBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            lst = new ArrayList<>();

            for (String gt : value) {
                UserGridBrief gbt = new UserGridBrief();

                if ((dbCommonService != null) && (gt != null)) {
                    String gid = dbCommonService.getGridIdbyFullname(getHospID(), gt);

                    if (gid != null) {
                        gbt.setGridId(gid);
                        gbt.setGridFullname(gt);
                        lst.add(gbt);
                    }
                }
            }
        }

        gridIds=lst;
    }

    public String getExstaffActivestateName() {
        String result = null;

        if ((exstaffActivestate != null) && (exstaffActivestate.compareTo("1") == 0))
            result = "激活";
        else {
            result = "非激活";
            exstaffActivestate = null;
        }

        exstaffActivestateName = result;
        return result;
    }


    /**
     * 获取物业职工工作状态名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getExstaffJobstateName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((exstaffJobstate != null) && (exstaffJobstate != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_job_state").getNamebyID(exstaffJobstate);
            } catch (Exception e) {
                throw new Exception("获取物业工作状态名称名称失败：" + e.getMessage());
            }

        exstaffJobstateName = result;
        if (exstaffJobstateName == null) exstaffJobstate = null;
        return result;
    }

    /**
     * 获取物业公司名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getExcompanyName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((excompanyId != null) && (excompanyId != ""))
            try {
                result = dbCommonService.getExcompanyNamebyId(excompanyId);
            } catch (Exception e) {
                throw new Exception("获取物业公司名称失败：" + e.getMessage());
            }

        excompanyName = result;
        if (excompanyName == null) excompanyId = null;
        return result;
    }

    /**
     * 获取服务班组名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getExteamName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((exteamId != null) && (exteamId != ""))
            try {
                result = dbCommonService.getExteamFullnamebyId(exteamId);
            } catch (Exception e) {
                throw new Exception("获取服务班组全名失败：" + e.getMessage());
            }

        exteamName = result;
        if (exteamName == null) exteamId = null;
        return result;
    }

}
