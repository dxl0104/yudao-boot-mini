package cn.iocoder.yudao.module.wuyou.service.sourceurl;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl.SourceUrlDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 列表链接 Service 接口
 *
 * @author 芋道源码
 */
public interface SourceUrlService {

    /**
     * 创建列表链接
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSourceUrl(@Valid SourceUrlSaveReqVO createReqVO);

    /**
     * 更新列表链接
     *
     * @param updateReqVO 更新信息
     */
    void updateSourceUrl(@Valid SourceUrlSaveReqVO updateReqVO);

    /**
     * 删除列表链接
     *
     * @param id 编号
     */
    void deleteSourceUrl(Long id);

    /**
     * 获得列表链接
     *
     * @param id 编号
     * @return 列表链接
     */
    SourceUrlDO getSourceUrl(Long id);

    /**
     * 获得列表链接分页
     *
     * @param pageReqVO 分页查询
     * @return 列表链接分页
     */
    PageResult<SourceUrlDO> getSourceUrlPage(SourceUrlPageReqVO pageReqVO);

    List<SourceUrlDO> getSourceUrlList();

}