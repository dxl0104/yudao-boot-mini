package cn.iocoder.yudao.module.wuyou.dal.dataobject.task;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 采集任务 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_task")
@KeySequence("wuyou_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 类型
     *
     * 枚举 {@link TODO task_type 对应的类}
     */
    private Integer taskType;
    /**
     * 状态
     *
     * 枚举 {@link TODO task_status 对应的类}
     */
    private Integer status;
    /**
     * 超时时间
     */
    private LocalDateTime timeoutAt;
    /**
     * 页数
     */
    private Integer pages;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    private String url;

    private String detailIds;

    private Integer isResolve;



}