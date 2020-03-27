package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;


import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;

import cn.edu.ujs.lp.intells.common.service.DBCommonService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * 组织机构实体
 *
 * @author Meredith
 * @data 2019-10-06
 */
@Entity
@Table(name = "tb_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department extends BaseHospEntity {

    /**
     * 部门组织机构编码(录入)
     */
    @Column(name = "dept_code",length = 50)
    private String deptCode;

    /**
     * 部门名称
     */
    @Column(name = "dept_name",length = 50)
    private String deptName;

    /**
     * 科室等级，从0开始，医院为0级
     */
    @Column(name = "dept_level")
    private int deptLevel;

    /**
     * 上级部门id(外键引用)
     */
    @Column(name = "superior_id",length = 32)
    private String superiorId;

    /**
     * 部门电话
     */
    @Column(name = "dept_tel",length = 15)
    private String deptTel;

    /**
     * 部门负责人
     */
    @Column(name = "dept_leader_name",length = 20)
    private String deptLeaderName;

    /**
     * 负责人电话
     */
    @Column(name = "dept_leader_tel",length = 15)
    private String deptLeaderTel;

    /**
     * 负责人Id
     */
    @Column(name="dept_leader_id",length = 32)
    private String deptLeaderId;

    /**
     * 部门的全路径
     */
    @Column(name = "dept_fullname")
    private String deptFullname;

    /**
     * 部门编码自动生成
     */
    @Column(name = "dept_selfcode",length = 20)
    private String deptSelfcode;

    /**
     * 父节点ID
     */
    @Transient
    private String superiorName;

    /**
     * 获取父节点名称
     * @param dbCommonService
     * @return
     * @throws Exception
     */
    public String getSuperiorName(DBCommonService dbCommonService) throws Exception {
        String result = null;

        if ((superiorId != null) && (superiorId != ""))
            try {
                result = dbCommonService.getDeptFullnamebyId(superiorId);
            } catch (Exception e) {
                throw new Exception("获取设备类别全名失败：" + e.getMessage());
            }

        superiorName = result;
        if (superiorName == null) superiorId = null;
        return result;
    }
}
