package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Exstaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 外委职工数据库访问类
 * 用于实现外委职工信息的存储、更新，
 */
@Repository
public interface ExstaffRepository extends JpaRepository<Exstaff,String>, JpaSpecificationExecutor<Exstaff>, CrudRepository<Exstaff,String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set exstaff_pic=null where id=?1", nativeQuery = true)
    int clearExstaff_pic(String Id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set exstaff_pic=?2 where id=?1", nativeQuery = true)
    int UpdateExstaff_pic(String Id,String fn);

}
