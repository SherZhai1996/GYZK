package cn.edu.ujs.lp.intells.common.entity.User;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 职工角色表
 *
 * @author Meredith
 * @data 2019-09-08
 */
@Entity
@Table(name = "tb_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole extends BaseEntity {
    /**
     * 职工id
     */
    @Column(name = "user_id",length = 32)
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;
}
