package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 组织结构Repository
 *
 * @author Meredith
 * @data 2019-10-06
 */
@Repository
public interface DeptRepository extends JpaRepository<Department,String >, JpaSpecificationExecutor<Department> {

}
