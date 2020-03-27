package cn.edu.ujs.lp.intells.basicinformation.dao.Excompany;

import cn.edu.ujs.lp.intells.basicinformation.entity.Excompany.Excompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExcompanyRepository extends JpaRepository<Excompany,String>, JpaSpecificationExecutor<Excompany>, CrudRepository<Excompany,String> {

    /**
     * 根据考生号查询
     * @param excompanyName
     * @return
     */
    Excompany findExcompanyByExcompanyName(String excompanyName);
    Long countByExcompanyNameContaining(String excompanyName);


    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_excompany set excompany_certificate=null where id=?1", nativeQuery = true)
    int clearExcompanycertificate(String excompanyId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_excompany set excompany_certificate=?2 where id=?1", nativeQuery = true)
    int updateExcompanycertificate(String excompanyId,String fn);
}
