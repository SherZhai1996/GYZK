package cn.edu.ujs.lp.intells.basicinformation.dao.hosp;

import cn.edu.ujs.lp.intells.basicinformation.entity.Hosp.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职工Repository
 *
 * @author Meredith
 * @data 2019-10-08
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff,String >, JpaSpecificationExecutor<Staff> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set staff_picture=null where id=?1", nativeQuery = true)
    int clearStaff_pic(String Id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_user set staff_picture=?2 where id=?1", nativeQuery = true)
    int UpdateStaff_pic(String Id,String fn);
}

