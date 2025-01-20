package cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 列表链接 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_source_url")
@KeySequence("wuyou_source_url_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceUrlDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 链接地址
     */
    private String listUrl;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 是否转换为任务 0-未转换 1-已转换
     */
    private Integer convertTask;

}