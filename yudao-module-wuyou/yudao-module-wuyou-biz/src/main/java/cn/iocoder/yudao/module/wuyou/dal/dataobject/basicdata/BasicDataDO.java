package cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 无忧基础数据 DO
 *
 * @author admin234
 */
@TableName("wuyou_basic_data")
@KeySequence("wuyou_basic_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicDataDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * url
     */
    private String url;
    /**
     * json数据
     */
    private String dataJson;
    /**
     * 快递费
     */
    private Integer delivery;
    /**
     * 分类
     */
    private String category;

}