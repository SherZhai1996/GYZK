package cn.edu.ujs.lp.intells.common.entity.Hosp;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 医院功能类
 */
@Entity
@Table(name = "tb_hosp_functionlist")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospFunction  extends BaseEntity {

    /**
     * 医院ID
     */
    @Column(name = "hosp_id")
    private String hospId;

    /**
     * 功能ID
     */
    @Column(name = "function_id")
    private String functionId;

    @Transient
    private String functionName;
}
