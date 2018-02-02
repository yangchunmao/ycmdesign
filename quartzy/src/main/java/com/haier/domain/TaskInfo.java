package com.haier.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ycm
 * @date 2018/1/29
 */
@Data
@NoArgsConstructor
public class TaskInfo implements Serializable {

    private int id = 0;
    /**任务名称*/
    private String jobName;
    /**任务分组*/
    private String jobGroup;
    /**任务描述*/
    private String jobDescription;
    /**任务状态*/
    private String jobStatus;
    /**任务表达式*/
    private String cronExpression;
    /**任务创建时间*/
    private String createTime;
}
