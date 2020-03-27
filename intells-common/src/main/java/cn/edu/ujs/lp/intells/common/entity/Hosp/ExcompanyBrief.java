package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ExcompanyBrief {

    private String id; //公司ID',

    private String excompanyName; //公司名称',

    private String excompanyLegal; //` varchar(50) DEFAULT NULL COMMENT '法人代表',

    private String excompanyLeaderID;

    private String excompanyLeadername; //` varchar(50) DEFAULT NULL COMMENT '负责人（项目经理）姓名',

    private String excompanyLeadertel; //` varchar(15) DEFAULT NULL COMMENT '负责人（项目经理）电话',

    private int excompanyStaffamount;  //int(6) DEFAULT NULL COMMENT '员工数量',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date excompanyServicestartdate; //` DATE DEFAULT NULL COMMENT '服务起始时间',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date excompanyServiceenddate; //DATE DEFAULT NULL COMMENT '服务结束时间',

    /**
     * 公司服务简介
     */
    private String servicesRemark;

    private String excompanyAddress; /// varchar(200) NOT NULL COMMENT '公司地址',

    /**
     * 依据负责人ID获取负责人姓名和电话
     * @param userService
     * @throws Exception
     */
    public void getLeader(UserService userService) throws Exception
    {
        if ((userService != null)&&(excompanyLeaderID != null) && (excompanyLeaderID != ""))
        {
            try {
                User user = userService.getByID(excompanyLeaderID);
                if (user != null) {
                    if (user.getUserName() != null) excompanyLeadername = user.getUserName();
                    if (user.getUserMobile() != null) excompanyLeadertel = user.getUserMobile();
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取物业公司负责人信息失败:"+e.getMessage());
            }
        }
    }
}
