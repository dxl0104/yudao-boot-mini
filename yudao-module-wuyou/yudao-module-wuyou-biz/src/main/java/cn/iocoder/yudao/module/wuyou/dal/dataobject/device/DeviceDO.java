package cn.iocoder.yudao.module.wuyou.dal.dataobject.device;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 采集器信息 DO
 *
 * @author 芋道源码
 */
@TableName("wuyou_device")
@KeySequence("wuyou_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 验证码出现时间
     */
    private LocalDateTime captchaTime;

}