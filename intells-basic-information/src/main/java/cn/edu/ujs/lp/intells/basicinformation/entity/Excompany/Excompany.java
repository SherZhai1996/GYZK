package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_excompany")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Excompany extends BaseHospEntity {

    @Column(name = "excompany_name",length = 20)
    private String excompanyName; //公司名称',

    @Column(name = "excompany_legal",length = 50)
    private String excompanyLegal; //` varchar(50) DEFAULT NULL COMMENT '法人代表',

    @Column(name = "excompany_leader_name",length = 50)
    private String excompanyLeadername; //` varchar(50) DEFAULT NULL COMMENT '负责人（项目经理）姓名',

    @Column(name = "excompany_leader_tel",length = 15)
    private String excompanyLeadertel; //` varchar(15) DEFAULT NULL COMMENT '负责人（项目经理）电话',

    @Column(name = "excompany_leader_ID",length = 32)
    private String excompanyLeaderID;  //varchar(32) DEFAULT NULL COMMENT '负责人（项目经理）ID(实现与外围员工表外键关联)，在选择外键关联时负责人姓名和电话可以员工表中自动提取',

    @Column(name = "excompany_staff_amount")
    private int excompanyStaffamount;  //int(6) DEFAULT NULL COMMENT '员工数量',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "excompany_service_startdate")
    private Date excompanyServicestartdate; //` DATE DEFAULT NULL COMMENT '服务起始时间',

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @Column(name = "excompany_service_enddate")
    private Date excompanyServiceenddate; //DATE DEFAULT NULL COMMENT '服务结束时间',

    @Column(name = "excompany_certificate",length = 400,updatable = false)
    private String excompanyCertificate; //` varchar(400) DEFAULT NULL COMMENT '营业执照（路径+文件名）',

    @Column(name = "excompany_complaint_tel",length = 15)
    private String excompanyComplainttel; //` varchar(15) DEFAULT NULL COMMENT '投诉电话',

    @Column(name = "services_remark",length = 400)
    private String servicesRemark; //` varchar(400) NOT NULL COMMENT '服务简介',

    @Column(name = "excompany_address",length = 200)
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
