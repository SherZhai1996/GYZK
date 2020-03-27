package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 职工信息（参与后勤管理的相关职工）
 *
 * @author Meredith
 * @data 2019-10-08
 */
@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Staff extends BaseHospEntity {

    /**
     * openid
     */
    @Column(name = "openid",length = 120)
    private String openid;

    /**
     * 姓名
     */
    @Column(name = "user_name",length = 20)
    private String staffName;

    /**
     * 性别(数据字典)
     */
    @Column(name = "user_sex",length = 32)
    private String staffSex;

    @Transient
    private String staffSexName;

    /**
     * 职工号码
     */
    @Column(name = "staff_code",length = 20)
    private String staffCode;

    /**
     * 手机号码
     */
    @Column(name = "user_mobile",length = 15)
    private String staffMobile;

    /**
     * 身份证号码
     */
    @Column(name = "staff_identification",length = 20)
    private String staffIdentification;

    /**
     * 所属科室部门id
     */
    @Column(name = "dept_id",length = 32)
    private String deptId;

    @Transient
    private String deptName;

    /**
     * 激活状态(1--激活,0--未激活)
     */
    @Column(name = "user_activestate")
    private String staffActivestatus;

    @Transient
    private String staffActivestatusName;

    /**
     * 是否有权使用医助(1--有权, 0--无权)
     */
    @Column(name = "isuse_medicalhelp")
    private String isuseMedicalhelp;

    @Transient
    private String isuseMedicalhelpName;

    /**
     * 职工照片图片文件名
     */
    @Column(name = "staff_picture",updatable = false)
    private String staffPicture;

    /**
     * 登录用户名,缺省时为手机号，自动填入
     */
    @Column(name = "user_login_name",length = 20)
    private String staffLoginName;

    /**
     * 用户昵称
     */
    @Column(name = "user_login_nickname",length = 20)
    private String staffLoginNickname;

    /**
     * 登入密码，MD5密文存放
     */
    @Column(name = "user_password",length = 32)
    private String staffLoginPassword;

    /**
     * 办公电话
     */
    @Column(name = "staff_tel")
    private String staffTel;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "staff_birthday")
    private Date staffBirthday;

    /**
     * 邮箱
     */
    @Column(name = "staff_email",length = 50)
    private String staffEmail;

    /**
     * 行政职务(数据字典)
     */
    @Column(name = "staff_adminis_position",length = 32)
    private String staffAdminisPosition;

    @Transient
    private String staffAdminisPositionName;

    /**
     * 技术职务(数据字典)
     */
    @Column(name = "staff_technical_position",length = 32)
    private String staffTechnicalPosition;

    @Transient
    private String staffTechnicalPositionName;

    /**
     * 教学职务(数据字典)
     */
    @Column(name ="staff_education_position",length = 32)
    private String staffEducationPosition;

    @Transient
    private String staffEducationPositionName;

    /**
     * 导师类型(数据字典)
     */
    @Column(name = "staff_tutor_type",length = 32)
    private String staffTutorType;

    @Transient
    private String staffTutorTypeName;

    /**
     * 学历(数据字典)
     */
    @Column(name = "staff_degree",length = 32)
    private String staffDegree;

    @Transient
    private String staffDegreeName;

    /**
     * 微信号
     */
    @Column(name = "staff_weixin",length = 50)
    private String staffWeixin;

    /**
     * QQ号
     */
    @Column(name = "staff_qq",length = 50)
    private String staffQq;

    @Column(name = "user_type")
    private String userType;

    /**
     * 全名
     */
    @Transient
    private String fullName;

    /**
     * 员工角色
     */
    @Transient
    private List<UserRoleBrief> roleIds;

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

    /**
     * 获取科室名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getDeptName(DBCommonService dbCommonService) throws Exception
    {
        String result = null;

        if ((deptId != null) && (deptId != "") && (dbCommonService != null))
        {
            try {
                result = dbCommonService.getDeptFullnamebyId(deptId);
            }
            catch (Exception e)
            {
                throw new Exception("获取科室名称失败:"+e.getMessage());
            }
        }

        deptName=result;
        if (deptName == null) deptId = null;
        return result;
    }

    /**
     * 设置科室ID
     * @param name
     * @param dbCommonService
     * @throws Exception
     */
    public void setDeptName(String name,DBCommonService dbCommonService) throws Exception
    {
        if ((name != null) && (name != "") && (dbCommonService != null))
        {
            try {
                deptId = dbCommonService.getDeptIdbyFullname(getHospID(),name);
            }
            catch (Exception e)
            {
                throw new Exception("获取科室ID失败:"+e.getMessage());
            }
        }
    }

    /**
     * 获取全名
     * @return
     */
    public String getFullName()
    {
        return getRemark();
    }

    public String getstaffActivestatusName() {
        String result = null;

        if ((staffActivestatus != null) && (staffActivestatus.compareTo("1") == 0))
            result = "激活";
        else {
            result = "非激活";
            staffActivestatus = "0";
        }

        staffActivestatusName = result;
        return result;
    }

    /**
     * 设置激活状态
     * @param name
     */
    public void setstaffActivestatusFromName(String name) {

        if ((name != null)&&(name.compareTo("激活")==0))
            staffActivestatus = "1";
        else
            staffActivestatus = "0";
    }

    /**
     * 获取使用医助手状态
     * @return
     */
    public String getIsuseMedicalhelpName() {
        String result = null;

        if ((isuseMedicalhelp != null) && (isuseMedicalhelp.compareTo("1") == 0))
            result = "可用";
        else
        {
            result = "禁用";
            isuseMedicalhelp = "0";
        }

        isuseMedicalhelpName = result;
        return result;
    }

    /**
     * 设置使用医助手状态ID
     * @param name
     */
    public void setIsuseMedicalhelpFromName(String name) {

        if ((name != null) && (name.compareTo("可用") == 0))
            isuseMedicalhelp = "1";
        else
            isuseMedicalhelp = "0";
    }


    /**
     * 获取员工性别
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffSexName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffSex != null) && (staffSex != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_sex").getNamebyID(staffSex);
            } catch (Exception e) {
                throw new Exception("获取职工性别名称失败：" + e.getMessage());
            }

        staffSexName = result;
        if (staffSexName == null) staffSex = null;
        return result;
    }

    /**
     * 设置员工性别
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffSexFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {
        if ((name != null) && (name != ""))
            try {
                staffSex = dataDictionaryService.getDataDictionary("db_sex").getIDbyName(name.replace(" ",""));
            }
            catch (Exception e)
            {
                throw new Exception("获取职工性别ID失败："+e.getMessage());
            }
    }

    /**
     * 获取员工行政职务
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffAdminisPositionName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffAdminisPosition != null) && (staffAdminisPosition != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_staff_adminis_position").getNamebyID(staffAdminisPosition);
            } catch (Exception e) {
                throw new Exception("获取职工行政职务名称失败：" + e.getMessage());
            }

        staffAdminisPositionName = result;
        if (staffAdminisPositionName == null) staffAdminisPosition = null;
        return result;
    }

    /**
     * 设置员工行政职务
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffAdminisPositionFromName(String name,DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((name != null) && (name != ""))
            try {
                staffAdminisPosition = dataDictionaryService.getDataDictionary("db_staff_adminis_position").getIDbyName(name.replace(" ", ""));
            } catch (Exception e) {
                throw new Exception("获取职工行政职务ID失败：" + e.getMessage());
            }
    }

    /**
     * 获取员工技术职称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffTechnicalPositionName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffTechnicalPosition != null) && (staffTechnicalPosition != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_staff_technical_position").getNamebyID(staffTechnicalPosition);
            } catch (Exception e) {
                throw new Exception("获取技术职务名称失败：" + e.getMessage());
            }

        staffTechnicalPositionName = result;
        if (staffTechnicalPositionName == null) staffTechnicalPosition = null;
        return result;
    }

    /**
     * 设置员工技术职称
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffTechnicalPositionFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {

        if ((name != null) && (name != ""))
            try {
                staffTechnicalPosition = dataDictionaryService.getDataDictionary("db_staff_technical_position").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("获取技术职务ID失败："+e.getMessage());
            }
    }

    /**
     * 获取员工教育职务
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffEducationPositionName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffEducationPosition != null) && (staffEducationPosition != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_staff_education_positon").getNamebyID(staffEducationPosition);
            } catch (Exception e) {
                throw new Exception("获取教育职务名称失败：" + e.getMessage());
            }

        staffEducationPositionName = result;
        if (staffEducationPositionName == null) staffEducationPosition = null;
        return result;
    }

    /**
     * 设置员工教育职务
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffEducationPositionFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {

        if ((name != null) && (name != ""))
            try {
                staffEducationPosition = dataDictionaryService.getDataDictionary("db_staff_education_positon").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("获取教育职务ID失败："+e.getMessage());
            }
    }

    /**
     * 获取员工导师名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffTutorTypeName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffTutorType != null) && (staffTutorType != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_staff_tutor_type").getNamebyID(staffTutorType);
            } catch (Exception e) {
                throw new Exception("获取导师名称失败：" + e.getMessage());
            }

        staffTutorTypeName = result;
        if (staffTutorTypeName == null) staffTutorType = null;
        return result;
    }

    /**
     * 设置员工导师名称
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffTutorTypeFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {

        if ((name != null) && (name != ""))
            try {
                staffTutorType = dataDictionaryService.getDataDictionary("db_staff_tutor_type").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("获取导师ID失败："+e.getMessage());
            }
    }

    /**
     * 获取员工学历
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getStaffDegreeName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((staffDegree != null) && (staffDegree != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_staff_degree").getNamebyID(staffDegree);
            } catch (Exception e) {
                throw new Exception("获取学位名称失败：" + e.getMessage());
            }

        staffDegreeName = result;
        if (staffDegreeName == null) staffDegree = null;
        return result;
    }

    /**
     * 设置员工学历
     * @param name
     * @param dataDictionaryService
     * @throws Exception
     */
    public void setStaffDegreeFromName(String name,DataDictionaryService dataDictionaryService) throws Exception
    {

        if ((name != null) && (name != ""))
            try {
                staffDegree = dataDictionaryService.getDataDictionary("db_staff_degree").getIDbyName(name);
            }
            catch (Exception e)
            {
                throw new Exception("获取学位ID失败："+e.getMessage());
            }
    }

}
