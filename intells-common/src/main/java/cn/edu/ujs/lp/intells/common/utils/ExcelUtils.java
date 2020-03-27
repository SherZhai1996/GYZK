package cn.edu.ujs.lp.intells.common.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.*;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


 /** excel相关工具类
 *
 * @author Meredith,
 * @data 2019-10-05
 * modified by betta.fli 2019.10.08
 */
public class ExcelUtils {

     /**
      * 获取Sheet对象
      * @param templateClass
      * @return
      */
    private static Sheet getSheet(Class templateClass) throws Exception{

        Sheet sheet = null;

        try {
            sheet = new Sheet(0);
            sheet.setClazz(templateClass);
            sheet.setSheetName("Sheet1");
            //设置单元格格式
            Map map = new HashMap();
            for (int i = 0; i < templateClass.getFields().length; i++) {
                map.put(i, 60 * 150);
            }
            sheet.setColumnWidthMap(map);

            TableStyle ts = new TableStyle();
            if (ts != null) {
                Font hft = new Font();
                hft.setFontName("黑体");
                hft.setBold(true);
                hft.setFontHeightInPoints((short)14);
                ts.setTableHeadFont(hft);
                ts.setTableHeadBackGroundColor(IndexedColors.GREY_25_PERCENT);

                Font ft = new Font();
                ft.setFontName("宋体");
                ft.setBold(false);
                ft.setFontHeightInPoints((short)12);
                ts.setTableContentFont(ft);
                ts.setTableContentBackGroundColor(IndexedColors.WHITE);
            }

            sheet.setTableStyle(ts);

            return sheet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("获取Sheet对象失败:"+e.getMessage());
        }
    }

     /**
      * 导出模板
      * @param templateClass
      * @param outputStream
      */
    public static void writeTemplate(Class templateClass, OutputStream outputStream) throws Exception{
        try{

            Sheet sheet=getSheet(templateClass);

            //生成模板
            ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);

            writer.write(null,sheet); //只生成模板，没有数据

            //关闭流
            writer.finish();
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();

            throw new Exception("导出模板失败:"+e.getMessage());
        }
    }

     /**
      * 导出Excel空模板
      * @param templateClass
      * @param response
      * @param templateName
      */
    public static void exportTemplate(Class templateClass,HttpServletResponse response,String templateName) throws Exception
    {
        try {
            // 返回给前端
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(templateName + ".xlsx", "UTF-8"));
            response.setHeader("Access-Control-Allow-Origin","*");

            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                    "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            writeTemplate(templateClass, response.getOutputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("导出Excel模板失败:"+e.getMessage());
        }
    }

     /**
      * 读取Excel数据
      * @param inputStream
      * @param readExcelClass
      * @return
      */
    public static List readData(InputStream inputStream,Class readExcelClass) throws Exception{
        List list;

        try {
            list = EasyExcelFactory.read(inputStream, new Sheet(0, 2, readExcelClass));

            if ((list != null) && (list.size() >= 2)) {
                list.remove(0);
                list.remove(0);
            } else
                list = null;

            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("读取Excel数据失败："+e.getMessage());
        }
    }

    /**
     * 导出数据到Excel
     */
    public static void writeData(Class templateClass, OutputStream outputStream, List<BaseRowModel> rows) throws Exception{
        try {

            Sheet sheet=getSheet(templateClass);

            // 生成模版
            ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
            writer.write(rows, sheet);

            // 关闭流
            writer.finish();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("导出数据到Excel失败:"+e.getMessage());
        }
    }

     /**
      * 导出Excel文件数据
      * @param templateClass
      * @param response
      * @param fileName
      * @param rows
      */
     public static void exportData(Class templateClass,HttpServletResponse response,String fileName,List<BaseRowModel> rows) throws Exception
     {
         try {
             // 返回给前端
             // 清除buffer缓存
             response.reset();
             response.setCharacterEncoding("UTF-8");
             //String encodedFileName = URLEncoder.encode(templateName + ".xlsx", "utf-8").replaceAll("\\+", "%20");
             String encodedFileName = fileName + ".xlsx";
             //String encodedFileName = new String((templateName + ".xlsx").getBytes("GB2312"), "ISO8859-1");
             response.setContentType("application/vnd.ms-excel");
             response.setHeader("Content-Disposition","attachment;filename="+encodedFileName);
             response.setHeader("Access-Control-Allow-Origin","*");

             response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " +
                     "WG-App-Version, WG-Device-Id, WG-Network-Type, WG-Vendor, WG-OS-Type, WG-OS-Version, WG-Device-Model, WG-CPU, WG-Sid, WG-App-Id, WG-Token");
             response.setHeader("Access-Control-Allow-Methods", "POST, GET");
             response.setHeader("Access-Control-Allow-Credentials", "true");

             writeData(templateClass, response.getOutputStream(),rows);
         }
         catch (Exception e)
         {
             e.printStackTrace();
             throw new Exception("导出Excel文件数据失败:"+e.getMessage());
         }
     }


     /**
      * 日期转换为字符串
      * @param value
      * @param format
      * @return
      * @throws Exception
      */
     public static String DateToString(Date value, String format) throws Exception
     {
         String rt = null;

         try {
             if (value != null) {
                 SimpleDateFormat formatter = new SimpleDateFormat(format);
                 ParsePosition pos = new ParsePosition(0);

                 rt = formatter.format(value);
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
             throw new Exception("日期转换为字符串失败:"+e.getMessage());
         }

         return rt;
     }

     /**
      * 字符串转换为日期
      * @param value
      * @param format
      * @return
      * @throws Exception
      */
     public static Date StringToDate(String value,String format) throws Exception
     {
         Date rt = null;

         try {
             if ((value != null) && (format != null) && (value.length()>=format.length())) {
                 SimpleDateFormat formatter = new SimpleDateFormat(format);
                 ParsePosition pos = new ParsePosition(0);

                 rt = formatter.parse(value, pos);
             }
         }
         catch (Exception e)
         {
             e.printStackTrace();
             throw new Exception("字符串转换为日期失败:"+e.getMessage());
         }

         return rt;
     }


     /**
      * 判断字符串是否为null
      * @param value
      * @return
      */
     public static boolean isNullofString(String value)
     {
         return ((value == null) ||(value.length() <=0));
     }
}
