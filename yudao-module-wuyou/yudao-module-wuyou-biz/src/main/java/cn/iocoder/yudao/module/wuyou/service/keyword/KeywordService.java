package cn.iocoder.yudao.module.wuyou.service.keyword;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;

import javax.validation.Valid;

/**
 * 无忧侵权词 Service 接口
 *
 * @author admin234
 */
public interface KeywordService {

    /**
     * 创建无忧侵权词
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createKeyword(@Valid KeywordSaveReqVO createReqVO);

    /**
     * 更新无忧侵权词
     *
     * @param updateReqVO 更新信息
     */
    void updateKeyword(@Valid KeywordSaveReqVO updateReqVO);

    /**
     * 删除无忧侵权词
     *
     * @param id 编号
     */
    void deleteKeyword(Long id);

    /**
     * 获得无忧侵权词
     *
     * @param id 编号
     * @return 无忧侵权词
     */
    KeywordDO getKeyword(Long id);

    /**
     * 获得无忧侵权词分页
     *
     * @param pageReqVO 分页查询
     * @return 无忧侵权词分页
     */
    PageResult<KeywordDO> getKeywordPage(KeywordPageReqVO pageReqVO);

}