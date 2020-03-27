package cn.edu.ujs.lp.intells.common.dao.User;

import cn.edu.ujs.lp.intells.common.entity.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,String>, JpaSpecificationExecutor<UserRole>, CrudRepository<UserRole,String> {
}
