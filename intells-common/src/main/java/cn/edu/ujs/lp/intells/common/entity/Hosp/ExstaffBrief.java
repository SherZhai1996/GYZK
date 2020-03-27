package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.UserGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.UserRoleBrief;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 外委职工主要数据对象
 */
@Data
public class ExstaffBrief {

    /**
     * ID
     */
    private String id;

    /**
     * 姓名
     */
    private String exstaffName; //` varchar(20) NOT NULL COMMENT '姓名',

    /**
     * 性别(数据字典)
     */
    private String exstaffSex; //` varchar(32) DEFAULT NULL COMMENT '性别(数据字典)',

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date exstaffBirthday; //` datetime DEFAULT NULL COMMENT '出生日期',

    /**
     * 手机号
     */
    private String exstaffMobile; //` varchar(15) NOT NULL COMMENT '手机号',

    /**
     * 职工工号
     */
    private String exstaffJobno; //` varchar(20) DEFAULT NULL COMMENT '职工工号',

    /**
     * 岗位状态(数据字典, db_job_state)
     */
    private String exstaffJobstate; //` varchar(32) DEFAULT NULL COMMENT '岗位状态(数据字典, db_job_state)',

    private String exstaffJobstateName;
    /**
     * 激活状态(1--激活, 0--未激活)
     */
    private String exstaffActivestate; //` tinyint(1) DEFAULT 0 COMMENT '激活状态(1--激活, 0--未激活)',

    private String exstaffActivestateName;

    /**
     * 所属公司（外键关联）
     */
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联）',

    private String excompanyName;
    /**
     * 所属班组（外键关联）
     */
    private String exteamId; //` varchar(32) NOT NULL COMMENT '所属班组（外键关联）',

    private String exteamName;
    /**
     * 聘用日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date exstaffEmploystartdate; //` datetime DEFAULT NULL COMMENT '聘用日期',

    /**
     * 终止日期
     */
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date exstaffEmployenddate; //` datetime DEFAULT NULL COMMENT '终止日期',

    /**
     * 紧急联系人姓名
     */
    private String exstaffEmercontactname; //` varchar(20) DEFAULT  NULL COMMENT '紧急联系人姓名',

    /**
     * 紧急联系电话
     */
    private String exstaffEmercontacttel; //` varchar(15) DEFAULT  NULL COMMENT '紧急联系电话',

    private List<UserRoleBrief> roleIds;

    private String roleStrings;

    public String getRoleStrings() {
        String lst = null;

        if ((roleIds != null) && (roleIds.size() > 0)) {
            for (UserRoleBrief srb : roleIds) {
                if (lst == null)
                {
                    lst = srb.getRoleName();
                }
                else
                    lst += ";"+srb.getRoleName();
            }
        }

        return lst;
    }

    private List<UserGridBrief> gridIds;

    private String gridNameStrings;

    public String getGridNameStrings() {
        String lst = null;

        if ((gridIds != null) && (gridIds.size() > 0)) {
            for (UserGridBrief gtb : gridIds) {
                if (lst == null)
                {
                    lst = gtb.getGridFullname();
                }
                else
                    lst += ";"+gtb.getGridFullname();
            }
        }

        return lst;
    }

    /**
     * 全名：公司->服务班组->职工名
     */
    private String remark;

    /**
     * 全名
     */
    private String fullName;

    /**
     * 获取全名
     * @return
     */
    public String getFullName()
    {
        return remark;
    }

    public String getExstaffActivestateName() {
        String result = null;

        if ((exstaffActivestate != null) && (exstaffActivestate.compareTo("1") == 0))
            result = "激活";
        else
        {
            result = "非激活";
            exstaffActivestate = "0";
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
