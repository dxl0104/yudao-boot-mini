package cn.iocoder.yudao.module.wuyou.dal.dataobject.category;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 数据类别 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_category")
@KeySequence("wuyou_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 类别名称
     */
    private String categoryName;
    /**
     * 中文名称
     */
    private String zhName;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 父id
     */
    private Long parentId;

}