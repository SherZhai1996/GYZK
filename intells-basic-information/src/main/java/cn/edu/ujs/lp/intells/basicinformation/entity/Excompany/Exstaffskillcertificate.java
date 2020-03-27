package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 外委员工技术证书类
 */
@Entity
@Table(name = "tb_exstaff_skillcertificate")
@Data
public class Exstaffskillcertificate  extends BaseEntity {

    /**
     * 外委员工ID
     */
    @Column(name = "exstaff_id",length = 32)
    private String exstaffId;

    /**
     技术证书ID
     */
    @Column(name = "skillcertificate_id",length = 32)
    private String skillcertificateId;

    /**
     技术证书图片
     */
    @Column(name = "skillcertificate_pic",updatable = false)
    private String skillcertificatePic;
}
