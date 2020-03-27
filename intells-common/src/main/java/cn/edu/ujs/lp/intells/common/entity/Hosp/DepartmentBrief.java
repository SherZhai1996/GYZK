package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.TreeInterface;
import lombok.Data;

@Data
public class DepartmentBrief implements TreeInterface {

    private String id;

    /**
     * 科室部门名称
     */
    private String deptName;

    /**
     * 组织机构代码
     */
    private String deptCode;

    private String deptTel;

    private String deptLeaderName;

    private String deptLeaderTel;

    private String superiorId;

    private String superiorName;

    private String deptFullname;

    private String deptSelfcode;

    private int deptLevel;

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
        return getDeptName();
    }

    public int getNodeLevel()
    {
        return getDeptLevel();
    }

    public String getNodeParentID()
    {
        return getSuperiorId();
    }

    public String getNodeParentName()
    {
        return getSuperiorName();
    }
}
