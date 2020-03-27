package cn.edu.ujs.lp.intells.common.dao.hosp;

import cn.edu.ujs.lp.intells.common.entity.Hosp.Hosp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医院Repository
 *
 * @author Meredith
 * @date 2019-09-17
 */
@Repository
public interface HospRepository extends JpaRepository<Hosp,String>{
    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_hospital_basic_information set hosp_badge=null where id=?1", nativeQuery = true)
    int clearHosp_badge(String hospId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_hospital_basic_information set hosp_picture=null where id=?1", nativeQuery = true)
    int clearHosp_picture(String hospId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_hospital_basic_information set hosp_badge=?2 where id=?1", nativeQuery = true)
    int updateHosp_badge(String hospId,String fn);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_hospital_basic_information set hosp_picture=?2 where id=?1", nativeQuery = true)
    int updateHosp_picture(String hospId,String fn);
}
