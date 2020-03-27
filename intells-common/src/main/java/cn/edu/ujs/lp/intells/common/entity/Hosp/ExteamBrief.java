package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.UserService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务班组主要数据对象
 */
@Data
public class ExteamBrief implements TreeInterface {

    private String id;

    private String exteamName; //` varchar(100) NOT NULL COMMENT '班组名称',

    private String exteamCode;

    /**
     * 服务部门
     */
    private String exservicesId; //` varchar(32) NOT NULL COMMENT '业务部门(外键关联，关联外委公司的业务部门)',

    private String exservicesName;

    /**
     * 外委公司
     */
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联），实现业务部门关联时自动提取',

    private String excompanyName;
    /**
     * 服务类型
     */
    private String serviceId;
    private String serviceName;

    private String exteamLeaderName; //` varchar(20) DEFAULT NULL COMMENT '班组负责人',

    private String exteamLeaderMobile; //` varchar(15) DEFAULT NULL COMMENT '负责人电话',

    private String exteamTel; //` varchar(15) DEFAULT NULL COMMENT '班组电话，不填，默认为班组长电话',

    private String exteamLeaderId; //` varchar(32) DEFAULT NULL COMMENT '班组负责人ID（外键引用），在选择外键关联时负责人姓名和电话可以员工表中自动提取',

    private int exteamStaffAmount; //` int(6) DEFAULT NULL COMMENT '服务员工数量，统计量，不用填写',

    private String superId; //` varchar(32) DEFAULT NULL COMMENT '父节点ID（外键关联），第一层节点父节点为Null',

    private String superName; //

    private String exteamFullname;

    private int exteamLevel; //` int(1) DEFAULT NULL COMMENT '班组等级，从0开始，医院为0级',

    private List<ExteamGridBrief> gridIds;

    private String gridNameString;

    public String getGridNameString() {
        String lst = null;

        if ((gridIds != null) && (gridIds.size() > 0)) {
            for (ExteamGridBrief gtb : gridIds) {
                if (lst == null) {
                    lst = gtb.getGridFullname();
                }
                else
                    lst += ";"+gtb.getGridFullname();
            }
        }

        return lst;
    }

    /**
     * 依据负责人ID获取负责人姓名和电话
     * @param userService
     * @throws Exception
     */
    public void getLeader(UserService userService) throws Exception
    {
        if ((userService != null)&&(exteamLeaderId != null) && (exteamLeaderId != ""))
        {
            try {
                User user = userService.getByID(exteamLeaderId);

                if (user != null) {
                    if (user.getUserName() != null) exteamLeaderName = user.getUserName();
                    if (user.getUserMobile() != null) exteamLeaderMobile = user.getUserMobile();
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取服务班组负责人信息失败:"+e.getMessage());
            }
        }
    }

    /**
     * 树形结构实现需要的方法，即对TreeInterface接口的实现
     * @return
     */
    public String getNodeID()
    {
        return getId();
    }

    public String getNodeName()
    {
        return getExteamName();
    }

    public int getNodeLevel()
    {
        return getExteamLevel();
    }

    public String getNodeParentID()
    {
        return getSuperId();
    }

    public String getNodeParentName()
    {
        return getSuperName();
    }
}
