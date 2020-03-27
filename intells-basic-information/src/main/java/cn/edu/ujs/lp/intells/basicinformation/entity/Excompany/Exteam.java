package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务班组数据对象
 */
@Entity
@Table(name = "tb_exteam")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exteam extends BaseHospEntity {

    /**
     * 班组名称
     */
    @Column(name = "exteam_name",length = 100)
    private String exteamName; //` varchar(100) NOT NULL COMMENT '班组名称',

    /**
     * 班组编号
     */
    @Column(name = "exteam_code",length = 20)
    private String exteamCode; //` varchar(100) NOT NULL COMMENT '班组名称',

    /**
     * 业务部门
     */
    @Column(name = "exservices_id",length = 32)
    private String exservicesId; //` varchar(32) NOT NULL COMMENT '业务部门(外键关联，关联外委公司的业务部门)',

    @Transient
    private String exservicesName;
    /**
     * 外委公司
     */
    @Column(name = "excompany_id",length = 32)
    private String excompanyId; //` varchar(32) NOT NULL COMMENT '所属公司（外键关联），实现业务部门关联时自动提取',

    @Transient
    private String excompanyName;

    /**
     * 服务类型
     */
    @Column(name = "service_id",length = 32)
    private String serviceId; //` varchar(32) DEFAULT NULL COMMENT '服务类型id(数据字典:服务类型)，可以从业务部门中继承而来，属于冗余',

    @Transient
    private String serviceName;

    /**
     * 服务班组组长姓名
     */
    @Column(name = "exteam_leader_name",length = 20)
    private String exteamLeaderName; //` varchar(20) DEFAULT NULL COMMENT '班组负责人',

    /**
     * 服务班组手机电话
     */
    @Column(name = "exteam_leader_mobile",length = 15)
    private String exteamLeaderMobile; //` varchar(15) DEFAULT NULL COMMENT '负责人电话',

    /**
     * 服务班组电话
     */
    @Column(name = "exteam_tel",length = 15)
    private String exteamTel; //` varchar(15) DEFAULT NULL COMMENT '班组电话，不填，默认为班组长电话',

    /**
     * 服务班组长ID
     */
    @Column(name = "exteam_leader_id",length = 32)
    private String exteamLeaderId; //` varchar(32) DEFAULT NULL COMMENT '班组负责人ID（外键引用），在选择外键关联时负责人姓名和电话可以员工表中自动提取',

    /**
     * 服务班组员工数，统计量
     */
    @Column(name = "exteam_staff_amount")
	private int exteamStaffAmount; //` int(6) DEFAULT NULL COMMENT '服务员工数量，统计量，不用填写',

    /**
     * 上机服务班组ID
     */
    @Column(name = "super_id",length = 32)
	private String superId; //` varchar(32) DEFAULT NULL COMMENT '父节点ID（外键关联），第一层节点父节点为Null',

    /**
     * 服务班组全名
     */
    @Column(name = "exteam_fullname")
    private String exteamFullname;

    /**
     * 服务班组层级
     */
    @Column(name = "exteam_level")
    private int exteamLevel; //` int(1) DEFAULT NULL COMMENT '班组等级，从0开始，医院为0级',

    @Transient
    private String superName;

    @Transient
    private List<ExteamGridBrief> gridIds;

    @Transient
    private String gridNameString;

    @Transient
    private List<List<String>> gridPathIds;

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
     * 获取责任网格的全名列表
     * @return
     */
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
     * 获取责任网格的全路径ID列表
     * @return
     */
    public List<List<String>> getGridPathbyId(DBCommonService dbCommonService) throws Exception
    {
        List<List<String>> lst = null;

        if ((dbCommonService != null) && (gridIds != null) && (gridIds.size() > 0)) {
            lst = new ArrayList<>();

            try {
                for (ExteamGridBrief gtb : gridIds) {
                    List<String> its = dbCommonService.getGridPathbyId(gtb.getGridId());
                    lst.add(its);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格ID获取责任网格全路径ID列表失败:"+e.getMessage());
            }
        }

        gridPathIds = lst;
        return lst;
    }

    public void setGridIdsFromStrings(List<String> value, DBCommonService dbCommonService) throws Exception
    {
        List<ExteamGridBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            lst = new ArrayList<>();

            for (String gt : value) {
                ExteamGridBrief gbt = new ExteamGridBrief();

                if ((dbCommonService != null) && (gt != null)) {
                    String gid = dbCommonService.getGridIdbyFullname(getHospID(), gt);

                    if (gid != null) {
                        gbt.setGridId(dbCommonService.getGridIdbyFullname(getHospID(), gt));
                        gbt.setGridFullname(gt);
                        lst.add(gbt);
                    }
                }
            }
        }

        gridIds=lst;
    }
}
