package cn.edu.ujs.lp.intells.basicinformation.entity.Excompany;

import cn.edu.ujs.lp.intells.common.entity.Common.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 外委员工餐饮证书类
 */
@Entity
@Table(name = "tb_exstaff_restaurantcertificate")
@Data
public class Exstaffrestaurantcertificate  extends BaseEntity {

    /**
     * 外委员工ID
     */
    @Column(name = "exstaff_id",length = 32)
    private String exstaffId;

    /**
     * 餐饮证书ID
     */
    @Column(name = "restaurantcertificate_id",length = 32)
    private String restaurantcertificateId;

    /**
     * 餐饮证书图片
     */
    @Column(name = "restaurantcertificate_pic",updatable = false)
    private String restaurantcertificatePic;
}
