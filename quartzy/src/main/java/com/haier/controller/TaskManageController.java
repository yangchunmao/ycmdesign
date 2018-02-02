package com.haier.controller;

import com.alibaba.fastjson.JSON;
import com.haier.domain.TaskInfo;
import com.haier.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2018/1/29.
 * @author ycm
 * 任务管理
 */
@Controller
public class TaskManageController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = {"", "/", "index"})
    public String info(){
        return "index.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String taskList(){
        Map<String,Object> map = new HashMap<>();
        List<TaskInfo> infos = taskService.taskList();
        map.put("rows", infos);
        map.put("total", infos.size());
        return JSON.toJSONString(map);
    }
}
