package cn.edu.ujs.lp.intells.common.entity.Hosp;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 医院功能主要属性类
 */
@Data
public class HospFunctionBrief {

    /**
     * 功能ID
     */
    private String functionId;

    private String functionName;
}
