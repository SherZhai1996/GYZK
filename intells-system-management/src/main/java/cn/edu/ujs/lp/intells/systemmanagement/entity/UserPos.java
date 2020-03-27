package cn.edu.ujs.lp.intells.systemmanagement.entity;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseHospEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户实时位置跟踪
 */
@Entity
@Table(name = "tb_user_pos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPos extends BaseHospEntity {

    @Column(name = "user_id",length = 32)
    private String userId;

    @Column(name = "grid_id",length = 32)
    private String gridId;

    @Column(name = "isexistibeacon")
    private String isexistibeacon;
}
