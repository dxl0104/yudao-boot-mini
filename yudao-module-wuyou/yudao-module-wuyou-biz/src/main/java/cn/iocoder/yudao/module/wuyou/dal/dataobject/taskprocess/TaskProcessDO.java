package cn.iocoder.yudao.module.wuyou.dal.dataobject.taskprocess;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 任务进度 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_task_process")
@KeySequence("wuyou_task_process_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskProcessDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 进度
     */
    private BigDecimal process;
    /**
     * 已采集数量
     */
    private Integer collectCount;
    /**
     * 总数量
     */
    private Integer totalCount;
    /**
     * 状态
     *
     * 枚举 {@link TODO task_process_status 对应的类}
     */
    private Integer status;

}