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
 * 职工网格关联表
 *
 * @author Meredith
 * @data 2019-10-08
 */
@Entity
@Table(name = "tb_user_hosp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserHosp extends BaseEntity {

    /**
     * 职工id
     */
    @Column(name = "user_id",length = 32)
    private String userId;

    /**
     * 网格id
     */
    @Column(name = "hosp_id",length = 32)
    private String hospId;
}
