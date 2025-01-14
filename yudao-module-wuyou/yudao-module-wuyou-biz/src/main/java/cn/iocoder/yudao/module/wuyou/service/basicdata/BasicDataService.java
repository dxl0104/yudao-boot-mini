package cn.iocoder.yudao.module.wuyou.service.basicdata;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 无忧基础数据 Service 接口
 *
 * @author admin234
 */
public interface BasicDataService {

    /**
     * 创建无忧基础数据
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBasicData(@Valid BasicDataSaveReqVO createReqVO);

    /**
     * 更新无忧基础数据
     *
     * @param updateReqVO 更新信息
     */
    void updateBasicData(@Valid BasicDataSaveReqVO updateReqVO);

    /**
     * 删除无忧基础数据
     *
     * @param id 编号
     */
    void deleteBasicData(Long id);

    /**
     * 获得无忧基础数据
     *
     * @param id 编号
     * @return 无忧基础数据
     */
    BasicDataDO getBasicData(Long id);

    /**
     * 获得无忧基础数据分页
     *
     * @param pageReqVO 分页查询
     * @return 无忧基础数据分页
     */
    PageResult<BasicDataDO> getBasicDataPage(BasicDataPageReqVO pageReqVO);

    void  importByIdS(List<String> ids);

}