package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.UserGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class StaffBrief {

    private String id;

    /**
     * 姓名
     */
    private String staffName;

    /**
     * 性别
     */
    private String staffSex;
    private String staffSexName;

    /**
     * 工号
     */
    private String staffCode;

    /**
     * 电话
     */
    private String staffMobile;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date staffBirthday;

    private String deptId;

    private String deptName;

    private String staffTel;

    private String staffAdminisPosition;
    private String staffAdminisPositionName;

    private String staffTechnicalPosition;
    private String staffTechnicalPositionName;

    private String staffEducationPosition;
    private String staffEducationPositionName;

    private String staffTutorType;
    private String staffTutorTypeName;

    private String staffDegree;
    private String staffDegreeName;

    /**
     * 激活状态
     */
    private String staffActivestatus;
    private String staffActivestatusName;

    /**
     * 是否实使用微信助手
     */
    private String isuseMedicalhelp;
    private String isuseMedicalhelpName;

    /**
     * 微信
     */
    private String openid;

    /**
     * 全名：科室名称->职工名
     */
    private String remark;

    /**
     * 全名
     */
    private String fullName;

    private List<UserRoleBrief> roleIds;

    private List<UserGridBrief> gridIds;

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
     * 获取全名
     * @return
     */
    public String getFullName()
    {
        return remark;
    }

    public String getstaffActivestatusName() {
        String result = null;

        if ((staffActivestatus != null) && (staffActivestatus.compareTo("1") == 0))
            result = "激活";
        else
        {
            result = "非激活";
            staffActivestatus = "0";
        }

        staffActivestatusName = result;
        return result;
    }

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
}
