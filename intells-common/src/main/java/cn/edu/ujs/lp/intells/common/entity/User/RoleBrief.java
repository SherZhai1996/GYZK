package cn.edu.ujs.lp.intells.common.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色信息主要信息类
 *
 * @author Meredith
 * @date 2019-09-20
 */

@Data
public class RoleBrief{

    /**
     * 角色id
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDec;

    /**
     * 角色类型，remark字段存储，"1"--系统, "2"--医院职工，"3"--外委职工
     */
    private String typeName;
}
