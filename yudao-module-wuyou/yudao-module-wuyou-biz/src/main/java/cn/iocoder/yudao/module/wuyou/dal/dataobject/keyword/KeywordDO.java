package cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 无忧侵权词 DO
 *
 * @author admin234
 */
@TableName("wuyou_keyword")
@KeySequence("wuyou_keyword_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 侵权词
     */
    private String infringementKeyword;
    /**
     * 平台
     */
    private Integer platform;

}