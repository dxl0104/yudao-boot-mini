package cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商品url列表 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_product_url")
@KeySequence("wuyou_product_url_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUrlDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * url
     */
    private String url;

}