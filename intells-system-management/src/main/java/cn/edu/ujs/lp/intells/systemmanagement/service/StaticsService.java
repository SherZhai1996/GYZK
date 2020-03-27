package cn.edu.ujs.lp.intells.systemmanagement.service;

import cn.edu.ujs.lp.intells.common.dao.hosp.HospMapper;
import cn.edu.ujs.lp.intells.common.dao.hosp.HospRepository;
import cn.edu.ujs.lp.intells.common.entity.Hosp.Hosp;
import cn.edu.ujs.lp.intells.systemmanagement.dao.StaticsMapper;
import cn.edu.ujs.lp.intells.systemmanagement.request.StaticsRequest;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StaticsService {
    @Autowired
    private StaticsMapper staticsMapper;

    //获取头
    @Autowired
    private HttpServletRequest header;

    @Autowired
    private HospRepository hospRepository;

    @Autowired
    private HospMapper hospMapper;


    /**
     * 统计数据
     * @param request
     * @return
     */
    public JsonResponse statistical(StaticsRequest request){
        try {

            List<Map> list = new ArrayList<>();
//        request.setHospId(header.getHeader("hosp-ID"));

            if (request.getHospId() == null || request.getHospId().equals("")) {
                //返回所有医院的数据
                List<Hosp> hosps = hospMapper.getHospbyid(null,null);

                for (int i = 0; i < hosps.size(); i++) {
                    Map map = new HashMap();
                    request.setHospId(hosps.get(i).getId());
                    map = staticsByHospId(request);
                    list.add(map);
                }
            } else {
                //查询一个医院
                Map m = new HashMap();
                m = staticsByHospId(request);
                list.add(m);

            }
            return JsonResponse.success("统计成功",list);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResponse.fail(1008,"统计失败");
        }

    }

    /**
     * 统计一个医院的数据
     * @param request
     * @return
     */
    private Map staticsByHospId(StaticsRequest request){
        Map m = new HashMap();
        //查询工单条数
        int a[] = new int[4];
        a[0] = staticsMapper.queryCountCompreRepairTaskSheet(request);
        a[1] = staticsMapper.queryCountCleaningTaskSheet(request);
        a[2] = staticsMapper.queryCountTransportTaskSheet(request);
        a[3] = staticsMapper.queryCountEquipTaskSheet(request);
        m.put("data", a);
//        Map data = new HashMap();
        //工单总数
//        data.put("综合维修",countMapper.queryCountCompreRepairTaskSheet(request));
//        data.put("应急保洁",countMapper.queryCountCleaningTaskSheet(request));
//        data.put("运送服务",countMapper.queryCountTransportTaskSheet(request));
//        data.put("设备维修",countMapper.queryCountEquipTaskSheet(request));
//        m.put("data",data);
        //投诉总数
//       Map count = new HashMap();
        //投诉总数
//       count.put("综合维修投诉",countMapper.queryCountComplaintCompreTaskSheet(request));
//       count.put("应急保洁投诉",countMapper.queryCountComplaintCleaningTaskSheet(request));
//       count.put("运送服务投诉",countMapper.queryCountComplaintTransportTaskSheet(request));
//       count.put("设备维修投诉",countMapper.queryCountComplaintEquipTaskSheet(request));
//       m.put("complaint",count);

        //查询好评工单数量
        int[] b = new int[4];
        b[0] = staticsMapper.queryCountComplaintCompreTaskSheet(request);
        b[1] = staticsMapper.queryCountComplaintCleaningTaskSheet(request);
        b[2] = staticsMapper.queryCountComplaintTransportTaskSheet(request);
        b[3] = staticsMapper.queryCountComplaintEquipTaskSheet(request);
        m.put("complaint", b);

        //已完工----未完工
        int[] c = new int[8];
        c[0] = staticsMapper.queryCountCompleteCompreTaskSheet(request);
        c[1] = a[0] - c[0];
        c[2] = staticsMapper.queryCountCompleteCleaingTaskSheet(request);
        c[3] = a[1] - c[2];
        c[4] = staticsMapper.queryCountCompleteTrasnportTaskSheet(request);
        c[5] = a[2] - c[4];
        c[6] = staticsMapper.queryCountCompleteEquipTaskSheet(request);
        c[7] = a[3] - c[6];
        Hosp hosp=hospRepository.findById(request.getHospId()).orElse(null);
        m.put("hospId",request.getHospId());
        m.put("hospName",hosp.getHospName());
        m.put("complete", c);

        return m;
    }

}
