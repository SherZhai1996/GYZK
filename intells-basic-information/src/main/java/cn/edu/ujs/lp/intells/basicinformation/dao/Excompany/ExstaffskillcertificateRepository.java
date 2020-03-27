package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaffskillcertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 外委职工技术证书数据库访问类
 * 用于实现技术证书记录的存储，图片文件数据更新与删除
 */
@Repository
public interface ExstaffskillcertificateRepository extends JpaRepository<Exstaffskillcertificate,String>, JpaSpecificationExecutor<Exstaffskillcertificate>, CrudRepository<Exstaffskillcertificate,String> {


}
