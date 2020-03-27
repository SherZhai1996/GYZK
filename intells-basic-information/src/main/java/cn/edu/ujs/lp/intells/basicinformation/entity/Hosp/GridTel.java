package cn.edu.ujs.lp.intells.basicinformation.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 网格电话从表，记录网格电话
 */
@Entity
@Table(name = "tb_grid_tel")
@Data
public class GridTel  extends BaseEntity {

    /**
     * 医院ID
     */
    @Column(name = "grid_id",length = 32)
    String grid_ID;

    /**
     电话
     */
    @Column(name = "grid_tel",length = 15)
    String grid_tel;
}
