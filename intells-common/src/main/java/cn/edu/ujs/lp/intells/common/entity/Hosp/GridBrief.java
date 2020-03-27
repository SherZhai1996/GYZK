package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class GridBrief implements TreeInterface {
    /**
     * 网格ID
     */
    private String id;

    /**
     * 网格名
     */
    private String gridName;

    /**
     * 网格代码
     */
    private String gridCode;

    /**
     * 网格全名
     */
    private String gridFullname;

    /**
     * 网格父节点ID
     */
    private String superiorGridid;

    private String superiorGridname;

    /**
     * 网格级别
     */
    private int gridLevel;

    /**
     * 网格面积
     */
    private double gridArea;

    /**
     * 网格用途类型
     */
    private String gridPlaceclassid;
    private String gridPlaceclassidName;

    /**
     * 网格所属科室
     */
    private String deptId;

    private String deptName;

    /**
     * 网格区域负责人姓名
     */
    private String gridLeaderName;

    /**
     * 网格区域负责人手机电话
     */
    private String gridLeaderTel;

    /**
     * 网格区域负责人ID
     */
    private String gridLeaderID;

    /**
     * 本网格的服务班组列表
     */
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
        return getGridName();
    }

    public int getNodeLevel()
    {
        return getGridLevel();
    }

    public String getNodeParentID()
    {
        return getSuperiorGridid();
    }

    public String getNodeParentName()
    {
        return getSuperiorGridname();
    }

}
