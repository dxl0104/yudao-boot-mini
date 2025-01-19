package cn.iocoder.yudao.module.wuyou.service.device;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.device.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.device.DeviceDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 采集器信息 Service 接口
 *
 * @author 芋道源码
 */
public interface DeviceService {

    /**
     * 创建采集器信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDevice(@Valid DeviceSaveReqVO createReqVO);

    /**
     * 更新采集器信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDevice(@Valid DeviceSaveReqVO updateReqVO);

    /**
     * 删除采集器信息
     *
     * @param id 编号
     */
    void deleteDevice(Long id);

    /**
     * 获得采集器信息
     *
     * @param id 编号
     * @return 采集器信息
     */
    DeviceDO getDevice(Long id);

    /**
     * 获得采集器信息分页
     *
     * @param pageReqVO 分页查询
     * @return 采集器信息分页
     */
    PageResult<DeviceDO> getDevicePage(DevicePageReqVO pageReqVO);

}