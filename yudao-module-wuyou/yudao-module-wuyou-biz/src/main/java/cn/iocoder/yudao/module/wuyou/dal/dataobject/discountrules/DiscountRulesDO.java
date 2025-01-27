package cn.iocoder.yudao.module.wuyou.dal.dataobject.discountrules;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 折扣规则 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_discount_rules")
@KeySequence("wuyou_discount_rules_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRulesDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 满足的最小总价
     */
    private BigDecimal minAmount;
    /**
     * 商品减免金额
     */
    private BigDecimal discountAmount;
    /**
     * 快递费用减免金额
     */
    private BigDecimal shippingDiscount;
    /**
     * 最大金额
     */
    private BigDecimal maxAmount;

}