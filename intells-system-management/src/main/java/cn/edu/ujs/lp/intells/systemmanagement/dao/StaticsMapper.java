package cn.edu.ujs.lp.intells.systemmanagement.dao;

import cn.edu.ujs.lp.intells.systemmanagement.request.StaticsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StaticsMapper {

    /**
     * 工单总数
     * @param request
     * @return
     */
    int queryCountCompreRepairTaskSheet(StaticsRequest request);

    int queryCountCleaningTaskSheet(StaticsRequest request);

    int queryCountTransportTaskSheet(StaticsRequest request);

    int queryCountEquipTaskSheet(StaticsRequest request);


    /**
     * 投诉总数
     * @param request
     * @return
     */
    int queryCountComplaintCompreTaskSheet(StaticsRequest request);

    int queryCountComplaintCleaningTaskSheet(StaticsRequest request);

    int queryCountComplaintTransportTaskSheet(StaticsRequest request);

    int queryCountComplaintEquipTaskSheet(StaticsRequest request);

    /**
     * 完成总数
     * @param request
     * @return
     */

    int queryCountCompleteCompreTaskSheet(StaticsRequest request);

    int queryCountCompleteCleaingTaskSheet(StaticsRequest request);

    int queryCountCompleteTrasnportTaskSheet(StaticsRequest request);

    int queryCountCompleteEquipTaskSheet(StaticsRequest request);
}
