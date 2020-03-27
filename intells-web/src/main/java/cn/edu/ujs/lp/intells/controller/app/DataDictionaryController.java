package cn.edu.ujs.lp.intells.controller.app;

import cn.edu.ujs.lp.intells.common.response.JsonResponse;
import com.alibaba.fastjson.JSONObject;
import cn.edu.ujs.lp.intells.common.entity.Common.DataDictionaryList;
import cn.edu.ujs.lp.intells.common.service.DataDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
数据字典控制接口类，实现对数据字典的访问，借助Redis实现内存缓存，以提高访问速度
 */
//@Controller
@Api(description = "数据字典接口")
@Controller
@CrossOrigin    //解决跨域问题
@RequestMapping(value = "/API/DataDictionary")
public class DataDictionaryController {
    @Autowired
    private DataDictionaryService dataDictionaryService;

    @ApiOperation(value = "数据字典初始化",notes = "数据字典初始化")
    @ResponseBody
    @GetMapping(value = "/Init")
    //@RequestMapping("/Init")
    public JsonResponse initialDataDictionary() {
        dataDictionaryService.initialDataDictionary();

        return JsonResponse.success ("数据字典初始化成功");
    }

    @ApiOperation(value = "获取指定类型的数据字典",notes = "获取指定类型的数据字典")
    @ResponseBody
    @GetMapping(value = "/getDataDictionary/{typename}")
    //@RequestMapping("/getDataDictionary/{typename}")
    public JsonResponse getDataDictionary(@PathVariable("typename") String typename) {

        DataDictionaryList ddl = dataDictionaryService.getDataDictionary(typename);

        if (ddl != null)
            return JsonResponse.success("获取数据字典成功!",ddl);
        else
            return JsonResponse.fail(10,"获取数据字典失败!");
    }

    @ApiOperation(value = "获取指定类型的数据字典字典项名称",notes = "获取指定类型的数据字典字典项名称")
    @ResponseBody
    @GetMapping(value = "/getNamebyID/{typename}/{id}")
    //@RequestMapping("/getNamebyID/{typename}/{id}")
    public JsonResponse getNamebyID(@PathVariable("typename") String typename,@PathVariable("id") String id) {

        JSONObject rt = null;
        DataDictionaryList dataDictionaryList = null;

        try {
            dataDictionaryList = dataDictionaryService.getDataDictionary(typename);

            if (dataDictionaryList != null)
            {
                String ts = dataDictionaryList.getNamebyID(id);

                if ((ts != null) && (ts.compareTo("") != 0))
                    rt = JSONObject.parseObject(String.format("{\"name\":\"%s\"}", ts));
            }
        }
        catch (Exception e)
        {
            rt=null;
        }

        if (rt == null)
            return JsonResponse.fail(11,"获取数据项名称失败!");
        else
            return JsonResponse.success("获取数据项名称成功!",rt);
    }

    @ApiOperation(value = "获取指定类型的数据字典字典项名称",notes = "获取指定类型的数据字典字典项名称")
    @ResponseBody
    @GetMapping(value = "/getIDbyName/{typename}/{name}")
    //@RequestMapping("/getIDbyName/{typename}/{name}")
    public JsonResponse getIDbyName(@PathVariable("typename") String typename,@PathVariable("name") String name) {
        JSONObject rt = null;
        DataDictionaryList dataDictionaryList = null;

        try {
            dataDictionaryList = dataDictionaryService.getDataDictionary(typename);

            if (dataDictionaryList != null)
            {
                String ts = dataDictionaryList.getIDbyName(name);

                if ((ts != null) && (ts.compareTo("") != 0))
                    rt = JSONObject.parseObject(String.format("{\"id\":\"%s\"}", ts));
            }
        }
        catch (Exception e)
        {
            rt=null;
        }

        if (rt == null)
            return JsonResponse.fail(11,"获取数据项ID失败!");
        else
            return JsonResponse.success("获取数据项ID成功!",rt);
    }

}
