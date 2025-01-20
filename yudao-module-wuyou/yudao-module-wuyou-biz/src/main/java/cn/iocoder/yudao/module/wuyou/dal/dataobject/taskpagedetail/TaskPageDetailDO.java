package cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 页数据采集状态 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_task_page_detail")
@KeySequence("wuyou_task_page_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskPageDetailDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 关联任务id
     */
    private Long taskId;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 状态
     *
     * 枚举 {@link TODO task_page_status 对应的类}
     */
    private Integer status;
    /**
     * 采集器id
     */
    private Long deviceId;
    /**
     * 分配时间
     */
    private LocalDateTime assignedAt;
    /**
     * 超时时间
     */
    private LocalDateTime timeout;
    /**
     * 链接
     */
    private String url;

}