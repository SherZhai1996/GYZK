package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.common.entity.Hosp.HospFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 医院功能集数据库访问类
 */
@Repository
public interface HospFunctionRepository extends JpaRepository<HospFunction,String> {
}
