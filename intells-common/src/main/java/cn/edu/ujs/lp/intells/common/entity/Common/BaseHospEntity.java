package cn.edu.ujs.lp.intells.common.entity.Common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 医院基础公共类，在基础类的基础上增加了医院ID
 */
@Data
@MappedSuperclass
public class BaseHospEntity extends BaseEntity{
    /**
     * 医院ID
     */
    @Column(name = "hosp_ID", length = 32)
    private String hospID;
}
