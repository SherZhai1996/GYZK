package cn.edu.ujs.lp.intells.common.dao.User;

import cn.edu.ujs.lp.intells.common.entity.User.UserHosp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHospRepository extends JpaRepository<UserHosp,String>, JpaSpecificationExecutor<UserHosp>, CrudRepository<UserHosp,String> {
}
