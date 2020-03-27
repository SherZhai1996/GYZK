package cn.edu.ujs.lp.intells.common.dao.User;

import cn.edu.ujs.lp.intells.common.entity.User.User;
//import cn.edu.ujs.lp.intells.common.entity.User.UserPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User>, CrudRepository<User,String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set user_pic_path=null where id=?1", nativeQuery = true)
    int clearUserlogopic(String Id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set user_pic_path=?2 where id=?1", nativeQuery = true)
    int UpdateUserlogopic(String Id,String fn);


}
