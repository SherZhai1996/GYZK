package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.UserService;
import lombok.Data;

@Data
public class ExServicesBrief {

    private String id; //业务部门ID',

    private String exserviceName; //业务部门名称',

    private String excompanyId; //所属外委公司ID',

    private String excompanyName;

    private String serviceId; //服务类型ID',

    private String serviceName;

    private String leaderName; //业务主管姓名',

    private String leaderTel; //业务主管电话',

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
