package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaffrestaurantcertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 外委职工餐饮证书数据库访问类，包括记录的保存操作,证书图片文件数据更新与删除
 */
@Repository
public interface ExstaffrestaurantcertificateRepository extends JpaRepository<Exstaffrestaurantcertificate,String>, JpaSpecificationExecutor<Exstaffrestaurantcertificate>, CrudRepository<Exstaffrestaurantcertificate,String> {

}
