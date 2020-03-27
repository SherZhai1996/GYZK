package cn.edu.ujs.lp.intells.common.service;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import cn.edu.ujs.lp.intells.common.utils.OSUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
文件上传服务，最大上传20M文件。
 */
@Service
public class FileService {

    /**
    上传指定的文件，参数分别为医院编码，类型（"sheet"--工单类,"device"--设备类，"basic"--基础信息类，"call"-来电类）和上传文件
     */
    public JsonResponse fileUpload(String hospcode, String typename, MultipartFile uploadfile) throws Exception {

        JSONObject rt = null;
        String rts = null;

        try {
            if (uploadfile.isEmpty()) {
                return JsonResponse.fail(21, "上传文件为空");
            } else {
                rts = onefileUpload(hospcode, typename, uploadfile); //返回存放文件相对路径文件名

                if (rts == null)
                    return JsonResponse.fail(21, "上传文件失败");
                else {
                    return JsonResponse.success("上传文件成功", rts);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("上传文件失败："+e.getMessage());
        }
    }

    /**
     *      上传指定的文件列表，参数分别为医院编码，类型（"sheet"--工单类,"device"--设备类，"basic"--基础信息类,"call"-来电类）和上传文件列表
     *      返回的文件列表中文件名之间用";"分割
     * @param hospcode
     * @param typename
     * @param uploadfiles
     * @return
     */
    public JsonResponse filesUpload(String hospcode, String typename, List<MultipartFile> uploadfiles) throws Exception{
        String rts = null;

        try {
            if ((uploadfiles == null) || (uploadfiles.size() <= 0)) {
                return JsonResponse.fail(21, "上传文件为空");
            } else {
                for (MultipartFile pf : uploadfiles) {
                    if (rts == null)
                        rts = onefileUpload(hospcode, typename, pf);
                    else {
                        String tmp = onefileUpload(hospcode, typename, pf);
                        if (tmp != null) rts += ";" + tmp;
                    }
                }

                if ((rts != null) && (rts.length() > 0)) {
                    return JsonResponse.success("上传文件列表成功", rts);
                } else
                    return JsonResponse.fail(21, "上传文件列表失败");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("上传文件列表失败："+e.getMessage());
        }
    }

    /**
     * 上传一个文件
     * @param hospcode
     * @param typename
     * @param uploadfile
     * @return
     */
    private String onefileUpload(String hospcode, String typename, MultipartFile uploadfile) throws Exception{

        try {
            if (uploadfile.isEmpty()) {
                return null;
            } else {
                if (uploadfile.getSize() > 1024 * 1024 * 100) {
                    return null;
                } else {
                    //定义存放文件的路径
                    String filepath;

                    if ((hospcode != null) && (hospcode != ""))
                        filepath = "/" + hospcode + "/";
                    else
                        filepath = "/system/";

                    if (typename.trim().compareTo("sheet") == 0) {
                        filepath += "sheet/";
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
                        filepath += formatter.format(currentTime);
                    } else if (typename.trim().compareTo("device") == 0) {
                        filepath += "device";
                    } else if (typename.trim().compareTo("call") == 0) {
                        filepath += "call/";
                        Date currentTime = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
                        filepath += formatter.format(currentTime);
                    } else {
                        filepath += "basic";
                    }

                    //上传文件的名字(包含后缀名)
                    String filename = uploadfile.getOriginalFilename();

                    //用UUID创建新的名字(没有后缀名)
                    String uuidname = UUID.randomUUID().toString().replace("-", "");

                    //把uuid创建的名字和文件后缀名截取出来合成一个新的完整的文件名
                    String saveFileName = uuidname + filename.substring(filename.lastIndexOf("."));

                    File savefile = new File(OSUtils.getBasefilepath() + filepath + "/" + saveFileName);

                    //判断父目录是否存在，不存在则创建目录
                    if (!savefile.getParentFile().exists()) {
                        savefile.getParentFile().mkdirs();
                    }

                    try {
                        //将图片保存到static文件夹里
                        uploadfile.transferTo(savefile);

                        String fn = filepath + "/" + saveFileName;
                        return fn;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("上传单个文件失败："+e.getMessage());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("上传单个文件失败："+e.getMessage());
        }
    }

    /**
    删除指定文件（带全路径）
     */
    public JsonResponse delFile(String filepath) throws Exception{

        try {
            if ((filepath != null) && (filepath.length() > 0)) {
                String filefullpath = OSUtils.getBasefilepath() + filepath;

                File file = new File(filefullpath);
                if (file.exists()) {

                    if (file.delete()) {
                        return JsonResponse.success("文件删除成功", filepath);
                    } else {
                        return JsonResponse.fail(24, "文件删除失败");
                    }
                } else {
                    return JsonResponse.success("文件不存在");
                }
            } else return JsonResponse.success("文件名为空");
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new Exception("删除单个文件失败:" + e.getMessage());
        }
    }

    /**
     * 删除多个文件，全路径文件之间用";"分割
     * @param filepaths
     * @return
     */
    public JsonResponse delFiles(String filepaths) throws Exception{

        try {
            if ((filepaths != null) && (filepaths.length() > 0)) {
                try {
                    String[] files = filepaths.split(";");
                    String rts = null;

                    for (String filepath : files) {
                        String filefullpath = OSUtils.getBasefilepath() + filepath;

                        File file = new File(filefullpath);

                        if (file.exists()) {
                            if (file.delete()) {
                                if (rts == null) rts = filepath;
                                else
                                    rts += ";" + filepath;
                            }
                        }
                    }

                    if (rts == null) {
                        return JsonResponse.fail(26, "删除文件失败");
                    } else {
                        return JsonResponse.success("删除文件成功", rts);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("删除文件列表失败："+e.getMessage());
                }
            } else
                return JsonResponse.success("文件为空，删除失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("删除文件列表失败："+e.getMessage());
        }
    }


    /**
     * 下载Excel文件
     * @param filepath
     * @param response
     * @throws Exception
     */
    public void downloadExcelFile(String filepath,HttpServletResponse response) throws Exception {
        try {
            File outfile = new File(filepath);
            String fileName = outfile.getName();

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                    "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            InputStream in = new FileInputStream(outfile);
            OutputStream out = response.getOutputStream();

            try {
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }
            catch (Exception e)
            {
                throw new Exception("读取下载文件失败:"+e.getMessage());
            }
            finally {
                in.close();
            }
        }
        catch (Exception e)
        {
            throw new Exception("下载文件失败:"+e.getMessage());
        }
    }

}
