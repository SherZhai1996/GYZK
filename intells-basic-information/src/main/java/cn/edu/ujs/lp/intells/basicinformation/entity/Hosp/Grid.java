package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;

import cn.edu.ujs.lp.intells.common.entity.Hosp.ExteamGridBrief;
import cn.edu.ujs.lp.intells.common.entity.User.User;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import cn.edu.ujs.lp.intells.common.service.UserService;
import cn.edu.ujs.lp.intells.common.utils.BaiduUtils;
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
 * 医院网格区域数据类，包括网格多边形点集、网格电话列表和网格信标列表等完整信息
 */
@Entity
@Table(name = "tb_grid")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grid  extends BaseHospEntity{

    /**
     * 网格代码
     */
    @Column(name = "grid_code",length = 30)
    private String gridCode;

    /**
     * 网格名称
     */
    @Column(name = "grid_name",length = 100)
    private String gridName;

    /**
     * 网格父节点ID
     */
    @Column(name = "superior_grid_id",length = 32)
    private String superiorGridid;

    /**
     * 网格级别
     */
    @Column(name = "grid_level")
    private int gridLevel;

    /**
     * 网格全名
     */
    @Column(name = "grid_fullname",length = 400)
    private String gridFullname;

    /**
     * 网格面积
     */
    @Column(name = "grid_area")
    private double gridArea;

    /**
     * 网格用途类型
     */
    @Column(name = "grid_placeclass_id",length = 32)
    private String gridPlaceclassid;

    @Transient
    private String gridPlaceclassidName;

    /**
     * 网格所属科室
     */
    @Column(name = "dept_id",length = 32)
    private String deptId;

    @Transient
    private String deptName;


    /**
     * 网格区域负责人姓名
     */
    @Column(name = "grid_leader_name",length = 20)
    private String gridLeaderName;

    /**
     * 网格区域负责人手机电话
     */
    @Column(name = "grid_leader_tel",length = 15)
    private String gridLeaderTel;

    /**
     * 网格区域负责人ID
     */
    @Column(name = "grid_leader_ID",length = 32)
    private String gridLeaderID;

    /**
     * 网格图片，不可修改
     */
    @Column(name = "grid_picture",updatable = false)
    private String gridPicture;

    /**
     * 中心点经度
     */
    @Column(name = "grid_centerX")
    private double gridcenterX = BaiduUtils.defaultCenterX;

    /**
     * 中心点纬度
     */
    @Column(name = "grid_centerY")
    private double gridcenterY = BaiduUtils.defaultCenterY;

    /**
     * 半径(单位米)
     */
    @Column(name = "grid_radius")
    private int gridradius = BaiduUtils.defaultbuildingRadius;

    /**
     * 父节点名称，冗余
     */
    @Transient
    private String superiorGridname;

    /**
     * 电话列表
     */
    @Transient
    private List<GridTelBrief> gridTels;

    /**
     *上一级网格节点的网格树
     */
    @Transient
    private List<List<String>> gridPathIds;

    /**
     * 物业服务班组列表
     */
    @Transient
    private List<String> gridExteams;

    /**
     * 获取本网格的服务班组
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public List<String> getGridExteam(DBCommonService dbCommonService) throws Exception {
        List<String> result = null;

        if (dbCommonService != null) {
            try {
                result = dbCommonService.getGridExteams(this.getId());
            }
            catch (Exception e)
            {
                throw new Exception("获取指定网格的服务班组失败:"+e.getMessage());
            }
        }

        gridExteams = result ;
        return result;
    }

    /**
     * 获取上级网络的全路径ID列表
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public List<List<String>>  getGridPathbyId(DBCommonService dbCommonService) throws Exception
    {
        List<List<String>> lst = null;

        if ((dbCommonService != null) && (superiorGridid != null) && (superiorGridid.length() > 0)) {
            try {
                lst = new ArrayList<>();
                List<String> its = dbCommonService.getGridPathbyId(superiorGridid);
                lst.add(its);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("依据网格ID获取上级节点网格全路径ID列表失败:"+e.getMessage());
            }
        }

        gridPathIds = lst;
        return lst;
    }

    /**
     * 依据负责人ID获取负责人姓名和电话
     * @param userService
     * @throws Exception
     */
    public void getLeader(UserService userService) throws Exception
    {
        if ((userService != null)&&(gridLeaderID != null) && (gridLeaderID != ""))
        {
            try {
                User user = userService.getByID(gridLeaderID);
                if (user != null) {
                    if (user.getUserName() != null) gridLeaderName = user.getUserName();
                    if (user.getUserMobile() != null) gridLeaderTel = user.getUserMobile();
                }
            }
            catch (Exception e)
            {
                throw new Exception("获取网格区域负责人信息失败:"+e.getMessage());
            }
        }
    }

    /**
     * 获取父节点名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getSuperiorGridname(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((superiorGridid != null) && (superiorGridid != ""))
            try {
                result = dbCommonService.getGridFullnamebyId(superiorGridid);
            } catch (Exception e) {
                throw new Exception("获取父节点名称失败：" + e.getMessage());
            }

        superiorGridname = result;
        if (superiorGridname == null) superiorGridid = null;
        return result;
    }

    /**
     * 获取科室（部门）名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getDeptName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((deptId != null) && (deptId != ""))
            try {
                result = dbCommonService.getDeptFullnamebyId(deptId);
            } catch (Exception e) {
                throw new Exception("获取科室（部门）名称失败：" + e.getMessage());
            }

        deptName = result;
        if (deptName == null) deptId = null;
        return result;
    }

    /**
     * 获取网格区域的用途类别名称
     * @param dataDictionaryService
     * @return
     * @throws Exception
     */
    public String getGridPlaceclassidName(DataDictionaryService dataDictionaryService) throws Exception {
        String result = null;

        if ((gridPlaceclassid != null) && (gridPlaceclassid != ""))
            try {
                result = dataDictionaryService.getDataDictionary("db_grid_placeclass_type").getNamebyID(gridPlaceclassid);
            } catch (Exception e) {
                throw new Exception("获取网格区域的用途类别名称失败：" + e.getMessage());
            }

        gridPlaceclassidName = result;
        if (gridPlaceclassidName == null) gridPlaceclassid = null;
        return result;
    }

    @Transient
    private String gridTelsString;

    @Transient
    private List<String> gridTelStrings;

    /**
     * 获取网格电话列表
     * @return
     */
    public List<String> getGridTelsString() {
        List<String> lst = null;

        gridTelsString = "";
        if ((gridTels != null) && (gridTels.size() > 0)) {
            lst = new ArrayList<>();

            for (GridTelBrief gtb : gridTels) {
                if ((gridTelsString == null) || (gridTelsString.length()<1))
                    gridTelsString = gtb.getGridTel();
                else
                    gridTelsString += ("; "+gtb.getGridTel());
                lst.add(gtb.getGridTel());
            }
        }

        gridTelStrings = lst;
        return lst;
    }

    /**
     * 设置网格电话
     * @param value
     */
    public void setGridTelsFromStrings(List<String> value)
    {
        List<GridTelBrief> lst = null;

        if ((value != null) && (value.size()>0))
        {
            lst = new ArrayList<>();

            for (String gt : value) {
                GridTelBrief gbt = new GridTelBrief();

                if (gbt != null) {
                    gbt.setGridTel(gt);
                    lst.add(gbt);
                }
            }
        }

        gridTels=lst;
    }

}
