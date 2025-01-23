package cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

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
    //0未采集  1-采集中 2-采集完成 3-采集超时
    private Integer processFlag;

    //上层列表链接地址
    private String categoryUrl;

    private Long deviceId;

    private LocalDateTime assignedAt;

}