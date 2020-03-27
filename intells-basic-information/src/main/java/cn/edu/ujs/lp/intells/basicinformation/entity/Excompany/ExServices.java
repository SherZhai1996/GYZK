package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_excompany_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExServices extends BaseHospEntity {

    @Column(name = "exservice_name",length = 32)
    private String exserviceName; //业务部门名称',

    @Column(name = "excompany_id",length = 32)
    private String excompanyId; //所属外委公司ID',

    @Transient
    private String excompanyName;

    @Column(name = "service_id",length = 32)
    private String serviceId; //服务类型ID',

    @Transient
    private String serviceName;

    @Column(name = "leader_name",length = 20)
    private String leaderName; //业务主管姓名',

    @Column(name = "leader_tel",length = 15)
    private String leaderTel; //业务主管电话',

    @Column(name = "leader_ID",length = 32)
    private String leaderID; //业务主管ID',

    /**
     * 依据负责人ID获取负责人姓名和电话
     * @param userService
     * @throws Exception
     */
    public void getLeader(UserService userService) throws Exception
    {
        if ((userService != null)&&(leaderID != null) && (leaderID != ""))
        {
            try {
                User user = userService.getByID(leaderID);
                if (user != null) {
                    if (user.getUserName() != null) leaderName = user.getUserName();
                    if (user.getUserMobile() != null) leaderTel = user.getUserMobile();
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取业务部门负责人信息失败:"+e.getMessage());
            }
        }
    }
}
