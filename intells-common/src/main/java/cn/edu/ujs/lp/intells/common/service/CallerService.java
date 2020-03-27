package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.dao.CallerRepository;
import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
服务中心电话处理服务类
 */

@Service
public class CallerService {
    @Autowired
    private CallerRepository callerRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private DBCommonService dbCommonService;


    /**
     上传指定文件
     */
    public JsonResponse uploadFile(String hospId, String typename,MultipartFile file) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "文件上传失败");

        try {
            if ((fileService != null) && (dbCommonService != null)){

                String hospCode = dbCommonService.getHospCodebyId(hospId);

                if (hospCode != null) {
                    rt = fileService.fileUpload(hospCode, typename, file);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("文件上传失败:"+e.getMessage());
        }

        return rt;
    }

    /**
     * 删除指定文件
     * @param filename
     * @return
     * @throws Exception
     */
    public JsonResponse deleteFile(String filename) throws Exception
    {
        JsonResponse rt = JsonResponse.fail(1006, "文件删除失败");

        try {
            if (fileService != null) {
                rt = fileService.delFile(filename);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("文件删除失败:"+e.getMessage());
        }

        return rt;
    }



    /**
     * 依据区域电话号码获取值班人员电话号码
     * @param gridTel
     * @return
     */
    public String getOndutyExStaffTelBygridTel(String gridTel)
    {
        String result = "";

        result = getexteam_leadertel_bygridtel(gridTel);

        if (result.isEmpty()) result ="18952819100";

        return result;
    }

    /**
     * 依据网格区域ID获取班组长电话
     * @param gridid
     * @return
     */
    public String getexteam_leadertel_bygridid(String gridid)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexteam_leadertel_bygridid(gridid);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        //if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域电话获取班组长电话
     * @param gridtel
     * @return
     */
    public String getexteam_leadertel_bygridtel(String gridtel)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexteam_leadertel_bygridtel(gridtel);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }


    /**
     * 依据网格区域电话获取班组长ID
     * @param gridtel
     * @return
     */
    public String getexteam_leaderID_bygridtel(String gridtel)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexteam_leaderID_bygridtel(gridtel);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域ID获取班组长ID
     * @param gridid
     * @return
     */
    public String getexteam_leaderID_bygridid(String gridid)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexteam_leaderID_bygridid(gridid);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域电话获取业务主管电话
     * @param gridtel
     * @return
     */
    public String getexcompanyservices_leadertel_bygridtel(String gridtel)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexcompanyservices_leadertel_bygridtel(gridtel);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域ID获取业务主管电话
     * @param gridid
     * @return
     */
    public String getexcompanyservices_leadertel_bygridid(String gridid)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexcompanyservices_leadertel_bygridid(gridid);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域电话获取项目经理电话
     * @param gridtel
     * @return
     */
    public String getexcompany_leadertel_bygridtel(String gridtel)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexcompany_leadertel_bygridtel(gridtel);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }

    /**
     * 依据网格区域ID获取项目经理电话
     * @param gridid
     * @return
     */
    public String getexcompany_leadertel_bygridid(String gridid)
    {
        String result = "";

        if (callerRepository != null)
        {
            try
            {
                List ls = callerRepository.getexcompany_leadertel_bygridid(gridid);
                if ((ls != null) && (ls.size()>0))
                {
                    result = ls.get(0).toString();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = "";
            }
        }

        if (result.isEmpty()) result ="";

        return result;
    }
}
