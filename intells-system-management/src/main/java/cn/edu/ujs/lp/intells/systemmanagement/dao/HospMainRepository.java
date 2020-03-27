package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.common.entity.Hosp.HospMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 医院系统管理数据数据库访问类
 */
@Repository
public interface HospMainRepository extends JpaRepository<HospMain,String> {

}
