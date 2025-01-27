package cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryconfig;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 快递费归档 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_delivery_config")
@KeySequence("wuyou_delivery_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryConfigDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 起始费用
     */
    private BigDecimal startMoney;
    /**
     * 结束费用
     */
    private BigDecimal endMoney;
    /**
     * 归档级别
     */
    private Integer level;

}